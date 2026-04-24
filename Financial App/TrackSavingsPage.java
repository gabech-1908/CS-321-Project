import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TrackSavingsPage {
    private static JPanel trackSavingsArea;
    private static JTextArea trackSavingsLabel;
    private static JTextArea savingAmountLabel;
    private static JTextArea savingNameLabel;
    private static JButton addSavings;
    

    public static void initTrackSavings(){
        trackSavingsArea = new JPanel();

        trackSavingsLabel = new JTextArea("Savings");
        savingAmountLabel = new JTextArea("Amount");
        savingNameLabel = new JTextArea("Name");

        addSavings = new JButton("Add Goals");
        addSavings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                switchFromTrackSavings();
                AddSavingGoalsPage.switchToAddSavingGoals();
            }
        });
        trackSavingsArea.setLayout(new GridLayout(2, 2));

        trackSavingsArea.add(trackSavingsLabel);
        trackSavingsArea.add(addSavings);

        trackSavingsArea.add(savingAmountLabel);
        trackSavingsArea.add(savingNameLabel);
        //TODO: need to display savings goals that have been set

        trackSavingsArea.setVisible(false);
    }


    public static void swtichToTrackSavings() {
        trackSavingsArea.setVisible(true);
    }

    public static void switchFromTrackSavings(){
        trackSavingsArea.setVisible(false);
    }
    
    public static JPanel getTrackSavingsArea(){
        return trackSavingsArea;
    }
}
