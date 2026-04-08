import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WeeklyOverviewPage {
    private static JPanel weeklyOverviewArea;
    private static JPanel displayExpensesArea;

    private static JTextArea dateArea;
    private static JTextArea weeklyAvgArea;

    private static JButton homeButton;
    private static JButton signOutButton;

    private static double weeklyAvg;

    public static void initWeeklyOverviewPage() {

        weeklyOverviewArea = new JPanel();
        displayExpensesArea = new JPanel();

        // hard code weekly avg
        weeklyAvg = 65.00;
        dateArea = new JTextArea("Week: 4/5/2026 - 4/11/2026");
        weeklyAvgArea = new JTextArea("Weekly Average Spending: $" + weeklyAvg);

        // larger font for readability
        Font bigFont = new Font("SansSerif", Font.PLAIN, 18);
        dateArea.setFont(bigFont);
        weeklyAvgArea.setFont(bigFont);

        // Use a vertical grid for each day on its own row
        JTextArea mon = new JTextArea("Monday");
        JTextArea tue = new JTextArea("Tuesday");
        JTextArea wed = new JTextArea("Wednesday");
        JTextArea thu = new JTextArea("Thursday");
        JTextArea fri = new JTextArea("Friday");
        JTextArea sat = new JTextArea("Saturday");
        JTextArea sun = new JTextArea("Sunday");

        mon.setFont(bigFont);
        tue.setFont(bigFont);
        wed.setFont(bigFont);
        thu.setFont(bigFont);
        fri.setFont(bigFont);
        sat.setFont(bigFont);
        sun.setFont(bigFont);

        displayExpensesArea.setLayout(new GridLayout(7, 1));
        displayExpensesArea.add(mon);
        displayExpensesArea.add(tue);
        displayExpensesArea.add(wed);
        displayExpensesArea.add(thu);
        displayExpensesArea.add(fri);
        displayExpensesArea.add(sat);
        displayExpensesArea.add(sun);

        // stack a top bar, date, weekly average, and the days panel vertically
        weeklyOverviewArea.setLayout(new BoxLayout(weeklyOverviewArea, BoxLayout.Y_AXIS));

        // Home button to return to HomePage
        homeButton = new JButton("Home");
        homeButton.setFont(bigFont);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfWeeklyOverview();
                HomePage.switchToHome();
            }
        });

        // Sign Out button to return to login page
        signOutButton = new JButton("Sign Out");
        signOutButton.setFont(bigFont);
        signOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfWeeklyOverview();
                // show login page
                HomePage.switchOutOfHome();
                LoginPage.switchToLogin();
            }
        });

        // top bar with Home left and Sign Out right
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.add(homeButton, BorderLayout.WEST);
        topBar.add(signOutButton, BorderLayout.EAST);

        weeklyOverviewArea.add(topBar);
        weeklyOverviewArea.add(dateArea);
        weeklyOverviewArea.add(weeklyAvgArea);
        weeklyOverviewArea.add(displayExpensesArea);

        weeklyOverviewArea.setVisible(false);
    }

    public static void switchToWeeklyOverview() {
        weeklyOverviewArea.setVisible(true);
        displayExpensesArea.setVisible(true);

        weeklyAvgArea.setVisible(true);
        dateArea.setVisible(true);
        if (homeButton != null) {
            homeButton.setVisible(true);
        }
        if (signOutButton != null) {
            signOutButton.setVisible(true);
        }
    }

    public static void switchOutOfWeeklyOverview() {
        weeklyOverviewArea.setVisible(false);
        displayExpensesArea.setVisible(false);
        weeklyAvgArea.setVisible(false);
        dateArea.setVisible(false);
        if (homeButton != null) {
            homeButton.setVisible(false);
        }
        if (signOutButton != null) {
            signOutButton.setVisible(false);
        }
    }

    public static JPanel getWeeklyOverviewArea() {
        return weeklyOverviewArea;
    }
}
