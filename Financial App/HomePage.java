
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class HomePage {

    private static JPanel homeArea;
    private static JButton weeklyOverviewButton;
    private static JButton monthlyOverviewButton;
    private static JButton signOutButton;
    private static JLabel mainText;

    private static JButton subscriptionButton;
    private static JButton incomeButton;
    private static JButton spendingButton;
    private static JButton savingsButton;

    private static JPanel weeklyTabPanel;
    private static JPanel monthlyTabPanel;

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
        subscriptionButton = new JButton("Add Subscriptions");
        incomeButton = new JButton("Add Income");
        spendingButton = new JButton("Add Spending");
        savingsButton = new JButton("Add Savings");

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.PLAIN, 16));
 
        weeklyTabPanel = new JPanel(new BorderLayout());
        monthlyTabPanel = new JPanel(new BorderLayout());
        tabs.addTab("Weekly Overview", weeklyTabPanel);
        tabs.addTab("Monthly Overview", monthlyTabPanel);
        
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

        spendingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchOutOfHome();
                SpendingPage.switchToSpending();
            }
        });

        savingsButton.addActionListener(e ->{
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
                .addComponent(tabs)
                .addGroup(
                    layout.createSequentialGroup()
                        .addComponent(weeklyOverviewButton)
                        .addComponent(monthlyOverviewButton)
                )
                .addGroup(
                    layout.createSequentialGroup()
                        .addComponent(subscriptionButton)
                        .addComponent(incomeButton)
                )
                .addGroup(
                    layout.createSequentialGroup()
                        .addComponent(spendingButton)
                        .addComponent(savingsButton)
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
                        .addComponent(tabs, 200, 250, 300)
                        .addGap(20)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(weeklyOverviewButton)
                                        .addComponent(monthlyOverviewButton)
                        )
                        .addGap(30)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(subscriptionButton)
                                        .addComponent(incomeButton)
                        )
                        .addGap(30)
                        .addGroup(
                            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(spendingButton)
                                .addComponent(savingsButton)
                        )
        );

        homeArea.setVisible(false);
    }

    // called every time we switch to home to refresh inputed data on the overview tabs
    private  static void refreshData() {
        
        
        double income = Database.getTotalIncome();
        double subscriptions = Database.getTotalMonthlySubscriptions();
        double spending = Database.getTotalSpending();

        if (weeklyTabPanel == null || monthlyTabPanel == null) return;
 
        // rebuild weekly tab with live spending data
        weeklyTabPanel.removeAll();
        JPanel newWeekly = buildWeeklyPanel();
        weeklyTabPanel.setLayout(new BorderLayout());
        weeklyTabPanel.add(newWeekly, BorderLayout.CENTER);
        weeklyTabPanel.revalidate();
        weeklyTabPanel.repaint();
 
        // rebuild monthly tab with live subscription data
        monthlyTabPanel.removeAll();
        JPanel newMonthly = buildMonthlyPanel();
        monthlyTabPanel.setLayout(new BorderLayout());
        monthlyTabPanel.add(newMonthly, BorderLayout.CENTER);
        monthlyTabPanel.revalidate();
        monthlyTabPanel.repaint();
    }

    //making the weekly tab
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

    //building the monnthly tab with a pie chart
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

    //switch purchases to a monthly amount based on frequency
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
        homeArea.revalidate();
        homeArea.repaint();
    }

    public static void switchOutOfHome() {
        homeArea.setVisible(false);
        homeArea.revalidate();
        homeArea.repaint();
    }
}
