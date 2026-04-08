
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomePage {

    private static JPanel homeArea;
    private static JButton weeklyOverviewButton;
    private static JButton monthlyOverviewButton;
    private static JButton signOutButton;
    private static JLabel mainText;

    public static void initHomePage() {
        //panel
        homeArea = new JPanel();

        //other components
        mainText = new JLabel("Home Page");
        mainText.setHorizontalAlignment(SwingConstants.CENTER);

        // logo
        JLabel logoLabel = new JLabel();
        ImageIcon icon = new ImageIcon(
            HomePage.class.getResource("/logo.png")
        );
        Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaled));

        //buttons
        weeklyOverviewButton = new JButton("Weekly Overview");
        monthlyOverviewButton = new JButton("Monthly Overview");
        signOutButton = new JButton("Sign Out");

        weeklyOverviewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfHome();
                //switch to weekly overview page
                WeeklyOverviewPage.switchToWeeklyOverview();
            }
        });

        monthlyOverviewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfHome();
                // switch to monthly overview page
                MonthlyOverviewPage.switchToMonthlyOverview();
            }
        });

        signOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfHome();
                // switch to login page
                LoginPage.switchToLogin();
            }
        });

        // Set up GroupLayout for precise positioning
        GroupLayout layout = new GroupLayout(homeArea);
        homeArea.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Horizontal group: Logo on left, Title centered, Sign Out on right
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(
                    layout.createSequentialGroup()
                        .addComponent(logoLabel)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(mainText)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(signOutButton)
                )
                .addGroup(
                    layout.createSequentialGroup()
                        .addComponent(weeklyOverviewButton)
                        .addComponent(monthlyOverviewButton)
                )
        );

        // Vertical group: Title at top, buttons lower down and centered
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(20)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(logoLabel)
                                    .addComponent(mainText)    
                                    .addComponent(signOutButton)
                                        
                        )
                        .addGap(100)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(weeklyOverviewButton)
                                        .addComponent(monthlyOverviewButton)
                        )
        );

        homeArea.setVisible(false);
    }

    public static JPanel getHomeArea() {
        return homeArea;
    }

    public static void switchToHome() {
        homeArea.setVisible(true);
        weeklyOverviewButton.setVisible(true);
        monthlyOverviewButton.setVisible(true);
        mainText.setVisible(true);
        signOutButton.setVisible(true);
    }

    public static void switchOutOfHome() {
        homeArea.setVisible(false);
    }
}
