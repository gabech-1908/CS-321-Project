import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
        Font bigFont = new Font("SansSerif", Font.PLAIN, 14);
        JPanel panel = new JPanel(new BorderLayout());
 
        JTextArea weeklyAvgArea = new JTextArea(
            String.format("Total Weekly Spending: $%.2f", Database.getTotalSpending())
        );
        weeklyAvgArea.setFont(bigFont);
        weeklyAvgArea.setEditable(false);
 
        // build spending list grouped by day
        JPanel displayExpensesArea = new JPanel(new GridLayout(0, 3, 4, 4));
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
 
        List<SpendingPage.SpendingEntry> entries = Database.getSpending();
 
        for (String day : days) {
            boolean addedDay = false;
            for (SpendingPage.SpendingEntry e : entries) {
                if (e.day.equals(day)) {
                    if (!addedDay) {
                        JTextArea dayLabel = new JTextArea(day);
                        dayLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                        dayLabel.setEditable(false);
                        displayExpensesArea.add(dayLabel);
                        addedDay = true;
                    } else {
                        displayExpensesArea.add(new JTextArea());
                    }
                    displayExpensesArea.add(new JTextArea(e.description));
                    displayExpensesArea.add(new JTextArea(String.format("$%.2f", e.amount)));
                }
            }
        }
 
        if (entries.isEmpty()) {
            JTextArea empty = new JTextArea("No spending entries yet.");
            empty.setEditable(false);
            panel.add(empty, BorderLayout.CENTER);
            return panel;
        }
 
        panel.add(weeklyAvgArea, BorderLayout.NORTH);
        panel.add(new JScrollPane(displayExpensesArea), BorderLayout.CENTER);
        return panel;
    }

    private static JPanel buildMonthlyPanel() {
        JPanel panel = new JPanel(new BorderLayout());
 
        List<Subscription> subs = Database.getSubscriptions();
        List<SpendingPage.SpendingEntry> spending = Database.getSpending();
 
        if (subs.isEmpty() && spending.isEmpty()) {
            JTextArea empty = new JTextArea("No data available.");
            empty.setEditable(false);
            panel.add(empty, BorderLayout.CENTER);
            return panel;
        }
 
        // build pie chart from subscription data
       java.util.LinkedHashMap<String, Double> combined = new java.util.LinkedHashMap<>();

        for (Subscription s : subs) {
            combined.merge(s.getTitle(), toMonthly(s.getAmount(), s.getFrequency()), Double::sum);
        }
        for (SpendingPage.SpendingEntry e : spending) {
            combined.merge(e.description, e.amount, Double::sum);
        }

        String[] labels = combined.keySet().toArray(new String[0]);
        double[] values = new double[labels.length];
        java.awt.Color[] colors = {
            new java.awt.Color(239, 83, 80),
            new java.awt.Color(66, 165, 245),
            new java.awt.Color(102, 187, 106),
            new java.awt.Color(255, 202, 40),
            new java.awt.Color(171, 71, 188),
            new java.awt.Color(255, 112, 67),
            new java.awt.Color(38, 166, 154)
        };

        double monthlyTotal = 0;
        for (int i = 0; i < labels.length; i++) {
            values[i] = combined.get(labels[i]);
            monthlyTotal += values[i];
        }

 
        JTextArea totalArea = new JTextArea(
            String.format("Total Monthly Spending: $%.2f", monthlyTotal)
        );
        totalArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        totalArea.setEditable(false);
 
        PieChartPanel chart = new PieChartPanel(labels, values, colors);
 
        panel.add(totalArea, BorderLayout.NORTH);
        panel.add(chart, BorderLayout.CENTER);
        return panel;
    }

    private static double toMonthly(double amount, String frequency) {
        switch (frequency) {
            case "Weekly":    return amount * 4.33;
            case "Bi-Weekly": return amount * 2.17;
            case "Monthly":   return amount;
            case "Quarterly": return amount / 3.0;
            case "Annually":  return amount / 12.0;
            default:          return amount;
        }
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
