import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class IncomePage {
 
    // simple class to store one income entry
    public static class IncomeEntry {
        String source;
        double amount;
 
        IncomeEntry(String source, double amount) {
            this.source = source;
            this.amount = amount;
        }
    }
 
    private static JPanel incomeArea;
    private static JTextField sourceInput;
    private static JTextField amountInput;
    private static JButton addButton;
    private static JButton backButton;
    private static JButton signoutButton;
    private static JTextArea incomeListArea;
    private static JLabel totalLabel;
    private static JLabel errorLabel;
 
    public static void initIncomePage() {
        incomeArea = new JPanel(new BorderLayout());
 
        //top section with home and sign out button
        JPanel topPanel = new JPanel(new BorderLayout());
        backButton = new JButton("Home");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfIncome();
                HomePage.switchToHome();
            }
        });
        signoutButton = new JButton("Sign Out");
        signoutButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                switchOutOfIncome();
                LoginPage.switchToLogin();
            }
        });
        
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(signoutButton, BorderLayout.EAST);
 
        //input sections
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        sourceInput = new JTextField("Income Source", 15);
        amountInput = new JTextField("Amount", 10);
        
 
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
 
        addButton = new JButton("Add Income");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleAdd();
            }
        });
 
        inputPanel.add(new JLabel("Income Source:"));
        inputPanel.add(sourceInput);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountInput);
        inputPanel.add(addButton);
        inputPanel.add(errorLabel);
 
        //list showing total spending
        JPanel bottomPanel = new JPanel(new BorderLayout());
        incomeListArea = new JTextArea(10, 30);
        incomeListArea.setEditable(false);
        incomeListArea.setText("Your income entries will appear here.\n");
 
        totalLabel = new JLabel("Total Monthly Income: $0.00");
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 
        bottomPanel.add(new JScrollPane(incomeListArea), BorderLayout.CENTER);
        bottomPanel.add(totalLabel, BorderLayout.SOUTH);
 
        incomeArea.add(topPanel, BorderLayout.NORTH);
        incomeArea.add(inputPanel, BorderLayout.CENTER);
        incomeArea.add(bottomPanel, BorderLayout.SOUTH);
 
        incomeArea.setVisible(false);
    }

    //error messages if any fields are left blank
    private static void handleAdd() {
        String source = sourceInput.getText().trim();
        String amountText = amountInput.getText().trim();
 
        // validate: source can't be empty
        if (source.isEmpty()){
            errorLabel.setText("Please enter a valid income source.");
            return;
        }
        // validate: amount must be a positive number
        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter a valid positive amount.");
            return;
        }
 
        // clear error, store and display
        errorLabel.setText(" ");
        Database.addIncome(source, amount);
        refreshList();
 
        // clear inputs
        sourceInput.setText("");
        amountInput.setText("");
    }
 
    private static void refreshList() {
        List<IncomeEntry> entries = Database.getIncome();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-10s%n", "Source", "Amount"));
        sb.append("-".repeat(45)).append("\n");

        double total = 0;
        for (IncomeEntry e : entries) {
            sb.append(String.format("%-20s $%-9.2f%n", e.source, e.amount));
            total += e.amount;
        }
        if (entries.isEmpty()) {
            sb.append("No income entries yet.");
        }
        incomeListArea.setText(sb.toString());
        totalLabel.setText(String.format("Total Monthly Income: $%.2f", total));
    }
 
    public static JPanel getIncomeArea() {
        return incomeArea;
    }
 
    public static void switchToIncome() {
        incomeArea.setVisible(true);
    }
 
    public static void switchOutOfIncome() {
        incomeArea.setVisible(false);
    }
}
