import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
 
public class SpendingPage {
 
    public static class SpendingEntry {
        public String description;
        public double amount;
        public String day;
 
        public SpendingEntry(String description, double amount, String day) {
            this.description = description;
            this.amount = amount;
            this.day = day;
        }
    }
 
    private static JPanel spendingArea;
    private static JTextField descriptionInput;
    private static JTextField amountInput;
    private static JComboBox<String> dayInput;
    private static JButton addButton;
    private static JButton backButton;
    private static JButton signoutButton;
    private static JTextArea spendingListArea;
    private static JLabel totalLabel;
    private static JLabel errorLabel;
 
    public static void initSpendingPage() {
        spendingArea = new JPanel(new BorderLayout());

        //top panel with home button and sign out button
        JPanel topPanel = new JPanel(new BorderLayout());
        backButton = new JButton("Home");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfSpending();
                HomePage.switchToHome();
            }
        });
        signoutButton = new JButton("Sign Out");
        signoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfSpending();
                LoginPage.switchToLogin();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(signoutButton, BorderLayout.EAST);
 
        //input sections
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
 
        descriptionInput = new JTextField("Description", 15);
        amountInput = new JTextField("Amount", 10);
        dayInput = new JComboBox<>(new String[]{
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        });
 
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
 
        addButton = new JButton("Add Spending");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAdd();
            }
        });
 
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionInput);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountInput);
        inputPanel.add(new JLabel("Day:"));
        inputPanel.add(dayInput);
        inputPanel.add(addButton);
        inputPanel.add(errorLabel);
 
        //list at the bottom showing the spending
        JPanel bottomPanel = new JPanel(new BorderLayout());
        spendingListArea = new JTextArea(10, 30);
        spendingListArea.setEditable(false);
        spendingListArea.setText("Your spending will appear here.\n");
 
        totalLabel = new JLabel("Total Spending: $0.00");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 
        bottomPanel.add(new JScrollPane(spendingListArea), BorderLayout.CENTER);
        bottomPanel.add(totalLabel, BorderLayout.SOUTH);
 
        spendingArea.add(topPanel, BorderLayout.NORTH);
        spendingArea.add(inputPanel, BorderLayout.CENTER);
        spendingArea.add(bottomPanel, BorderLayout.SOUTH);
 
        spendingArea.setVisible(false);
        refreshList();
    }
 
    private static void handleAdd() {
        String description = descriptionInput.getText().trim();
        String amountText = amountInput.getText().trim();
        String day = (String) dayInput.getSelectedItem();
 
        if (description.isEmpty() || description.equals("Description")) {
            errorLabel.setText("Please enter a description.");
            return;
        }
 
        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter a valid positive amount.");
            return;
        }
 
        errorLabel.setText(" ");
        Database.addSpending(description, amount, day);
        refreshList();
        descriptionInput.setText("");
        amountInput.setText("");
    }
 
    public static void refreshList() {
        if (spendingListArea == null) return;
        List<SpendingEntry> entries = Database.getSpending();
 
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s %s%n", "Description", "Amount", "Day"));
        sb.append("-".repeat(45)).append("\n");
 
        double total = 0;
        for (SpendingEntry e : entries) {
            sb.append(String.format("%-20s $%-9.2f %s%n", e.description, e.amount, e.day));
            total += e.amount;
        }
 
        if (entries.isEmpty()) {
            sb.append("No spending entries yet.\n");
        }
 
        spendingListArea.setText(sb.toString());
        totalLabel.setText(String.format("Total Spending: $%.2f", total));
    }
 
    public static JPanel getSpendingArea() {
        return spendingArea;
    }
 
    public static void switchToSpending() {
        refreshList();
        spendingArea.setVisible(true);
    }
 
    public static void switchOutOfSpending() {
        spendingArea.setVisible(false);
    }
}
