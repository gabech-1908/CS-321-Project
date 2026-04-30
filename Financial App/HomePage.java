import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class HomePage {

    private static JPanel homeArea;

    //private static JButton weeklyOverviewButton;
    //private static JButton monthlyOverviewButton;
    private static JButton signOutButton;

    private static JButton subscriptionButton;
    private static JButton incomeButton;
    private static JButton spendingButton;
    private static JButton savingsButton;

    private static JLabel mainText;

    private static JPanel weeklyTabPanel;
    private static JPanel monthlyTabPanel;

    public static void initHomePage() {

        homeArea = new JPanel(new BorderLayout());
        homeArea.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        Font titleFont = new Font("SansSerif", Font.BOLD, 28);
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 16);

        mainText = new JLabel("Home Page");
        mainText.setFont(titleFont);
        mainText.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel logoLabel = new JLabel();
        Dimension logoSize = new Dimension(160, 80);
        setLogoIconIfFound(logoLabel, logoSize);

        //weeklyOverviewButton = new JButton("Weekly Overview");
        //monthlyOverviewButton = new JButton("Monthly Overview");
        signOutButton = new JButton("Sign Out");

        subscriptionButton = new JButton("Add Subscriptions");
        incomeButton = new JButton("Add Income");
        spendingButton = new JButton("Add Spending");
        savingsButton = new JButton("Add Savings");

        JButton[] buttons = {
            //weeklyOverviewButton,
            //monthlyOverviewButton,
            subscriptionButton,
            incomeButton,
            spendingButton,
            savingsButton,
            signOutButton
        };

        Dimension buttonSize = new Dimension(180, 40);

        for (JButton b : buttons) {
            b.setFont(buttonFont);
            b.setFocusPainted(false);
            b.setPreferredSize(buttonSize);
            b.setMaximumSize(buttonSize);
        }

        // Make sign out smaller (override after loop)
        Dimension smallButton = new Dimension(120, 30);
        signOutButton.setPreferredSize(smallButton);
        signOutButton.setMaximumSize(smallButton);

        // Tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(buttonFont);

        weeklyTabPanel = new JPanel(new BorderLayout());
        monthlyTabPanel = new JPanel(new BorderLayout());

        tabs.addTab("Weekly Overview", weeklyTabPanel);
        tabs.addTab("Monthly Overview", monthlyTabPanel);

        // Actions
        //weeklyOverviewButton.addActionListener(e -> {
       //     switchOutOfHome();
         //   WeeklyOverviewPage.switchToWeeklyOverview();
        //});

        //monthlyOverviewButton.addActionListener(e -> {
        //    switchOutOfHome();
        //    MonthlyOverviewPage.switchToMonthlyOverview();
        //});

        signOutButton.addActionListener(e -> {
            switchOutOfHome();
            LoginPage.switchToLogin();
        });

        spendingButton.addActionListener(e -> {
            switchOutOfHome();
            SpendingPage.switchToSpending();
        });

        savingsButton.addActionListener(e -> {
            switchOutOfHome();
            TrackSavingsPage.swtichToTrackSavings();
        });

        subscriptionButton.addActionListener(e -> {
            switchOutOfHome();
            SubscriptionPage.switchToSubscription();
        });

        incomeButton.addActionListener(e -> {
            switchOutOfHome();
            IncomePage.switchToIncome();
        });

        // ===== HEADER (perfect centering fix) =====
        JPanel header = new JPanel(new BorderLayout());
        header.add(logoLabel, BorderLayout.WEST);
        header.add(mainText, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);
        rightPanel.add(signOutButton);

        header.add(rightPanel, BorderLayout.EAST);

        // ===== CENTER =====
        //JPanel overviewButtons = new JPanel(new GridLayout(1, 2, 15, 0));
        //overviewButtons.add(weeklyOverviewButton);
        //overviewButtons.add(monthlyOverviewButton);

        JPanel actionButtons = new JPanel(new GridLayout(2, 2, 15, 15));
        actionButtons.add(subscriptionButton);
        actionButtons.add(incomeButton);
        actionButtons.add(spendingButton);
        actionButtons.add(savingsButton);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        center.add(tabs);
        center.add(Box.createVerticalStrut(20));
        //center.add(overviewButtons);
        center.add(Box.createVerticalStrut(25));
        center.add(actionButtons);

        homeArea.add(header, BorderLayout.NORTH);
        homeArea.add(center, BorderLayout.CENTER);

        homeArea.setVisible(false);
    }

    private static void setLogoIconIfFound(JLabel label, Dimension size) {
        try {
            URL url = HomePage.class.getResource("/logo.png");
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);

                int w = icon.getIconWidth();
                int h = icon.getIconHeight();

                double scale = Math.min(
                    size.getWidth() / w,
                    size.getHeight() / h
                );

                int newW = (int)(w * scale);
                int newH = (int)(h * scale);

                Image img = icon.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
            } else {
                label.setText("Logo");
            }
        } catch (Exception e) {
            label.setText("Logo");
        }
    }

    private static void refreshData() {
        if (weeklyTabPanel == null || monthlyTabPanel == null) return;

        weeklyTabPanel.removeAll();
        weeklyTabPanel.add(buildWeeklyPanel(), BorderLayout.CENTER);
        weeklyTabPanel.revalidate();
        weeklyTabPanel.repaint();

        monthlyTabPanel.removeAll();
        monthlyTabPanel.add(buildMonthlyPanel(), BorderLayout.CENTER);
        monthlyTabPanel.revalidate();
        monthlyTabPanel.repaint();
    }

    private static JPanel buildWeeklyPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(
            String.format("Total Weekly Spending: $%.2f", Database.getTotalSpending())
        );
        label.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        panel.add(label, BorderLayout.NORTH);
        return panel;
    }

    private static JPanel buildMonthlyPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(
            String.format("Total Monthly Spending: $%.2f", Database.getTotalSpending())
        );
        label.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        panel.add(label, BorderLayout.NORTH);
        return panel;
    }

    public static JPanel getHomeArea() {
        return homeArea;
    }

    public static void switchToHome() {
        refreshData();
        homeArea.setVisible(true);
    }

    public static void switchOutOfHome() {
        homeArea.setVisible(false);
    }
}
