import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddSavingGoalsPage {
    private static JPanel addSavingsArea;

    private static JTextArea addSavingsLabel;
    private static JTextArea goalAmountLabel;
    private static JTextArea nameOfGoalLabel;
    private static JTextArea goalDeadlineLabel;

    private static JTextField goalAmount;
    private static JTextField nameOfGoal;
    private static JTextField goalDeadline;

    private static JButton setGoal;

    public static void initAddSavingsGoals(){
        addSavingsLabel = new JTextArea("Fill in goal amount and name");
        goalAmountLabel = new JTextArea("Goal Amount");
        nameOfGoalLabel = new JTextArea("Name");
        goalDeadlineLabel = new JTextArea("Deadline (Optional)");

        goalAmount = new JTextField();
        nameOfGoal = new JTextField();
        goalDeadline = new JTextField("Optional");

        setGoal = new JButton("Set Goal");
        setGoal.addActionListener(new ActionListener() {
            float amount = -1;
            String name;
            Date deadline = new Date(0);
            public void actionPerformed(ActionEvent e) {
                if(goalAmount.getText() != null){
                    amount = Float.parseFloat(goalAmount.getText());
                }

                if(nameOfGoal.getText() != null){
                    name = nameOfGoal.getText();
                }
                if(amount > 0){
                    addSavingsLabel.setText("Please enter a valid amount"); 
                } else if(name == null){
                    addSavingsLabel.setText("Please name the goal");
                } else if(name == null && amount > 0){
                    addSavingsLabel.setText("Please enter a valid amount and name the goal");
                }

                if(deadline != null){
                    User.addSavingsGoal(new SavingGoal(amount, name, deadline));
                } else{
                    User.addSavingsGoal(new SavingGoal(amount, name));
                }
                switchFromAddSavingsGoals();
                TrackSavingsPage.swtichToTrackSavings();
            }
        });

        addSavingsArea = new JPanel();
        addSavingsArea.setLayout(new GridLayout(2, 2));

        addSavingsArea.add(goalAmountLabel);
        addSavingsArea.add(goalAmount);
        addSavingsArea.add(nameOfGoalLabel);
        addSavingsArea.add(nameOfGoal);
        addSavingsArea.add(goalDeadlineLabel);
        addSavingsArea.add(goalDeadline);
        addSavingsArea.add(setGoal);

        addSavingsArea.setVisible(false);

    }

    public static void switchToAddSavingGoals(){
        addSavingsArea.setVisible(true);
    }

    public static void switchFromAddSavingsGoals(){
        addSavingsArea.setVisible(false);
    }

    public static JPanel getAddSavingsArea(){
        return addSavingsArea;
    }
}
