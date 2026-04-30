import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

public class SubscriptionPage {

    private static JPanel subscriptionsArea;
    private static JTextField titleInput;
    private static JTextField amountInput;
    private static JComboBox<String> frequencyInput;
    private static JButton addButton;
    private static JButton backButton;
    private static JButton signoutButton;
    private static JTextArea subscriptionListArea;
    private static JLabel totalLabel;
    private static JLabel errorLabel;

    public static void initSubscriptionPage() {
        subscriptionsArea = new JPanel(new BorderLayout());

        // ── North: back button + title ───────────────────────────────────────
        JPanel topPanel = new JPanel(new BorderLayout());
        backButton = new JButton("Home");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfSubscription();
                HomePage.switchToHome();
            }
        });
        signoutButton = new JButton("Sign Out");
        signoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfSubscription();
                LoginPage.switchToLogin();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(signoutButton, BorderLayout.EAST);

        // ── Center: input fields + add button + error label ──────────────────
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        titleInput = new JTextField("Service Name", 15);
        amountInput = new JTextField("Amount", 10);
        frequencyInput = new JComboBox<>(new String[] {
                "Weekly", "Bi-Weekly", "Monthly", "Quarterly", "Annually"
        });

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);

        addButton = new JButton("Add Subscription");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAdd();
            }
        });

        inputPanel.add(new JLabel("Service Name:"));
        inputPanel.add(titleInput);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountInput);
        inputPanel.add(new JLabel("How often due:"));
        inputPanel.add(frequencyInput);
        inputPanel.add(addButton);
        inputPanel.add(errorLabel);

        // subscription list and total
        JPanel bottomPanel = new JPanel(new BorderLayout());
        subscriptionListArea = new JTextArea(10, 30);
        subscriptionListArea.setEditable(false);
        subscriptionListArea.setText("Your subscriptions will appear here.\n");

        totalLabel = new JLabel("Total Monthly Cost: $0.00");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        bottomPanel.add(new JScrollPane(subscriptionListArea), BorderLayout.CENTER);
        bottomPanel.add(totalLabel, BorderLayout.SOUTH);

        subscriptionsArea.add(topPanel, BorderLayout.NORTH);
        subscriptionsArea.add(inputPanel, BorderLayout.CENTER);
        subscriptionsArea.add(bottomPanel, BorderLayout.SOUTH);

        subscriptionsArea.setVisible(false);
        refreshList();
    }

    private static void handleAdd() {
        String title = titleInput.getText().trim();
        String amountText = amountInput.getText().trim();
        String frequency = (String) frequencyInput.getSelectedItem();

        // validate: title can't be empty
        if (title.isEmpty() || title.equals("Service Name")) {
            errorLabel.setText("Please enter a service name.");
            return;
        }

        // validate: amount must be a positive number
        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter a valid positive amount.");
            return;
        }

        // clear error, store and display
        errorLabel.setText(" ");
        Database.addSubscription(title, amount, frequency);
        refreshList();

        // clear inputs
        titleInput.setText("");
        amountInput.setText("");
    }

    private static void refreshList() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s %s%n", "Service", "Amount", "Frequency"));
        sb.append("-".repeat(45)).append("\n");

        double monthlyTotal = 0;
        List<Subscription> subscriptions = Database.getSubscriptions();
        if (subscriptions.isEmpty()) {
            sb.append("No subscriptions added yet.");
        } else {
            for (Subscription s : subscriptions) {
                sb.append(String.format("%-20s $%-9.2f %s%n", s.getTitle(), s.getAmount(), s.getFrequency()));
                monthlyTotal += toMonthly(s.getAmount(), s.getFrequency());
            }
        }

        subscriptionListArea.setText(sb.toString());
        totalLabel.setText(String.format("Total Monthly Cost: $%.2f", monthlyTotal));
    }

    // converts any frequency to a monthly equivalent
    private static double toMonthly(double amount, String frequency) {
        switch (frequency) {
            case "Weekly":
                return amount * 4.33;
            case "Bi-Weekly":
                return amount * 2.17;
            case "Monthly":
                return amount;
            case "Quarterly":
                return amount / 3.0;
            case "Annually":
                return amount / 12.0;
            default:
                return amount;
        }
    }

    public static JPanel getSubscriptionArea() {
        return subscriptionsArea;
    }

    public static void switchToSubscription() {
        refreshList();
        subscriptionsArea.setVisible(true);
    }

    public static void switchOutOfSubscription() {
        subscriptionsArea.setVisible(false);
    }
}
