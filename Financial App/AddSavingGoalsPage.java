import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddSavingGoalsPage {
    private static JPanel addSavingsArea;

    private static JLabel addSavingsLabel;
    private static JLabel goalAmountLabel;
    private static JLabel nameOfGoalLabel;
    private static JLabel goalDeadlineLabel;

    private static JTextField goalAmount;
    private static JTextField nameOfGoal;
    private static JTextField goalDeadline;

    private static JButton setGoal;
    private static JButton backButton;
    private static JButton signoutButton;

    public static void initAddSavingsGoals() {

        // MAIN PANEL (matches SpendingPage)
        addSavingsArea = new JPanel(new BorderLayout());

        // ===== TOP PANEL (Home + Sign Out) =====
        JPanel topPanel = new JPanel(new BorderLayout());

        backButton = new JButton("Home");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchFromAddSavingsGoals();
                HomePage.switchToHome();
            }
        });

        signoutButton = new JButton("Sign Out");
        signoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchFromAddSavingsGoals();
                LoginPage.switchToLogin();
            }
        });

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(signoutButton, BorderLayout.EAST);

        // ===== INPUT PANEL =====
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));

        addSavingsLabel = new JLabel("Set a Savings Goal");
        goalAmountLabel = new JLabel("Goal Amount:");
        nameOfGoalLabel = new JLabel("Goal Name:");
        goalDeadlineLabel = new JLabel("Deadline (Optional):");

        goalAmount = new JTextField();
        nameOfGoal = new JTextField();
        goalDeadline = new JTextField();

        setGoal = new JButton("Set Goal");

        setGoal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSetGoal();
            }
        });

        inputPanel.add(addSavingsLabel);
        inputPanel.add(new JLabel(""));

        inputPanel.add(goalAmountLabel);
        inputPanel.add(goalAmount);

        inputPanel.add(nameOfGoalLabel);
        inputPanel.add(nameOfGoal);

        inputPanel.add(goalDeadlineLabel);
        inputPanel.add(goalDeadline);

        inputPanel.add(setGoal);
        inputPanel.add(new JLabel(""));

        // ===== ADD PANELS =====
        addSavingsArea.add(topPanel, BorderLayout.NORTH);
        addSavingsArea.add(inputPanel, BorderLayout.CENTER);

        addSavingsArea.setVisible(false);
    }

    private static void handleSetGoal() {
        double amount;
        String name = nameOfGoal.getText().trim();
        String deadlineText = goalDeadline.getText().trim();

        try {
            amount = Double.parseDouble(goalAmount.getText().trim());
            if (amount <= 0) throw new NumberFormatException();
        } catch (Exception e) {
            addSavingsLabel.setText("Enter a valid positive amount");
            return;
        }

        if (name.isEmpty()) {
            addSavingsLabel.setText("Please enter a goal name");
            return;
        }

        try {
            if (!deadlineText.isEmpty()) {
                Date deadline = Date.valueOf(deadlineText);
                User.addSavingsGoal(new SavingGoal(amount, name, deadline));
            } else {
                User.addSavingsGoal(new SavingGoal(amount, name));
            }
        } catch (Exception e) {
            addSavingsLabel.setText("Invalid date format (YYYY-MM-DD)");
            return;
        }

        addSavingsLabel.setText("Goal added!");
        switchFromAddSavingsGoals();
        TrackSavingsPage.swtichToTrackSavings();
    }

    public static void switchToAddSavingGoals() {
        addSavingsArea.setVisible(true);
    }

    public static void switchFromAddSavingsGoals() {
        goalAmount.setText("");
        nameOfGoal.setText("");
        goalDeadline.setText("");
        addSavingsArea.setVisible(false);
    }

    public static JPanel getAddSavingsArea() {
        return addSavingsArea;
    }
}