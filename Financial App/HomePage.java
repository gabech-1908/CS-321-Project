import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class HomePage {
    private static JPanel homeArea;
    private static JButton weeklyOverviewButton;
    private static JTextArea mainText;

    public static void initHomePage(){
        //panel
        homeArea = new JPanel();
        
        //other components
        mainText = new JTextArea("Home Page");
        //buttons
        weeklyOverviewButton = new JButton("Weekly Overview");

        weeklyOverviewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                switchOutOfHome();
                //switch to weekly overview page
                WeeklyOverviewPage.switchToWeeklyOverview();
            }
        });

        //add componenets to home page
        homeArea.add(mainText);
        homeArea.add(weeklyOverviewButton);

        homeArea.setVisible(false);
    }
    
    public static JPanel getHomeArea(){
        return homeArea;
    }

    public static void switchToHome(){
        homeArea.setVisible(true);
        weeklyOverviewButton.setVisible(true);
        mainText.setVisible(true);
    }

    public static void switchOutOfHome(){
        homeArea.setVisible(false);
    }
}