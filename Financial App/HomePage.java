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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

public class HomePage {

    private static JPanel homeArea;

    private static JButton weeklyOverviewButton;
    private static JButton monthlyOverviewButton;
    private static JButton signOutButton;

    private static JButton subscriptionButton;
    private static JButton incomeButton;
    private static JButton spendingButton;
    private static JButton savingsButton;

    private static JLabel mainText;

    private static JPanel weeklyTabPanel;
    private static JPanel monthlyTabPanel;

    public static void initHomePage() {
        homeArea = new JPanel();
        homeArea.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        Font titleFont = new Font("SansSerif", Font.BOLD, 28);
        Font buttonFont = new Font("SansSerif", Font.PLAIN, 16);

        mainText = new JLabel("Home Page");
        mainText.setFont(titleFont);
        mainText.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel logoLabel = new JLabel();
        Dimension logoSize = new Dimension(160, 80);
        setLogoIconIfFound(logoLabel, logoSize);
        logoLabel.setPreferredSize(logoSize);
        logoLabel.setMaximumSize(logoSize);

        weeklyOverviewButton = new JButton("Weekly Overview");
        monthlyOverviewButton = new JButton("Monthly Overview");
        signOutButton = new JButton("Sign Out");

        subscriptionButton = new JButton("Add Subscriptions");
        incomeButton = new JButton("Add Income");
        spendingButton = new JButton("Add Spending");
        savingsButton = new JButton("Add Savings");

        JButton[] buttons = {
            weeklyOverviewButton,
            monthlyOverviewButton,
            signOutButton,
            subscriptionButton,
            incomeButton,
            spendingButton,
            savingsButton
        };

        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setFocusPainted(false);
        }

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(buttonFont);

        weeklyTabPanel = new JPanel(new BorderLayout());
        monthlyTabPanel = new JPanel(new BorderLayout());

        tabs.addTab("Weekly Overview", weeklyTabPanel);
        tabs.addTab("Monthly Overview", monthlyTabPanel);

        weeklyOverviewButton.addActionListener(e -> {
            switchOutOfHome();
            WeeklyOverviewPage.switchToWeeklyOverview();
        });

        monthlyOverviewButton.addActionListener(e -> {
            switchOutOfHome();
            MonthlyOverviewPage.switchToMonthlyOverview();
        });

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

        JPanel overviewButtonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        overviewButtonPanel.add(weeklyOverviewButton);
        overviewButtonPanel.add(monthlyOverviewButton);

        JPanel actionButtonPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        actionButtonPanel.add(subscriptionButton);
        actionButtonPanel.add(incomeButton);
        actionButtonPanel.add(spendingButton);
        actionButtonPanel.add(savingsButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(tabs);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(overviewButtonPanel);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(actionButtonPanel);

        // ===== HEADER PANEL (fixes overlap) =====
        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(mainText, BorderLayout.CENTER);
        headerPanel.add(signOutButton, BorderLayout.EAST);

        // center the title properly
        mainText.setHorizontalAlignment(SwingConstants.CENTER);

        // ===== MAIN LAYOUT =====
        homeArea.setLayout(new BorderLayout());

        homeArea.add(headerPanel, BorderLayout.NORTH);
        homeArea.add(centerPanel, BorderLayout.CENTER);

        homeArea.setVisible(false);
    }

    private static void setLogoIconIfFound(JLabel logoLabel, Dimension logoSize) {
        ImageIcon icon = null;

        URL logoUrl = HomePage.class.getResource("/logo.png");

        if (logoUrl != null) {
            icon = new ImageIcon(logoUrl);
        }

        if (icon != null && icon.getIconWidth() > 0 && icon.getIconHeight() > 0) {
            Image scaled = getScaledImage(icon, logoSize);
            logoLabel.setText("");
            logoLabel.setIcon(new ImageIcon(scaled));
        } else {
            logoLabel.setText("Logo");
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private static Image getScaledImage(ImageIcon icon, Dimension maxSize) {
        int originalWidth = icon.getIconWidth();
        int originalHeight = icon.getIconHeight();

        double widthRatio = maxSize.getWidth() / originalWidth;
        double heightRatio = maxSize.getHeight() / originalHeight;
        double scale = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        return icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    private static void refreshData() {
        if (weeklyTabPanel == null || monthlyTabPanel == null) {
            return;
        }

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
        Font normalFont = new Font("SansSerif", Font.PLAIN, 14);
        Font boldFont = new Font("SansSerif", Font.BOLD, 14);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel weeklyTotalLabel = new JLabel(
            String.format("Total Weekly Spending: $%.2f", Database.getTotalSpending())
        );
        weeklyTotalLabel.setFont(normalFont);
        weeklyTotalLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel expensesPanel = new JPanel(new GridLayout(0, 3, 8, 8));
        expensesPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        String[] days = {
            "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday", "Sunday"
        };

        List<SpendingPage.SpendingEntry> entries = Database.getSpending();

        if (entries.isEmpty()) {
            JLabel emptyLabel = new JLabel("No spending entries yet.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(emptyLabel, BorderLayout.CENTER);
            return panel;
        }

        for (String day : days) {
            boolean addedDay = false;

            for (SpendingPage.SpendingEntry entry : entries) {
                if (entry.day.equals(day)) {
                    JLabel dayLabel = new JLabel(addedDay ? "" : day);
                    JLabel descriptionLabel = new JLabel(entry.description);
                    JLabel amountLabel = new JLabel(String.format("$%.2f", entry.amount));

                    dayLabel.setFont(boldFont);
                    descriptionLabel.setFont(normalFont);
                    amountLabel.setFont(normalFont);

                    expensesPanel.add(dayLabel);
                    expensesPanel.add(descriptionLabel);
                    expensesPanel.add(amountLabel);

                    addedDay = true;
                }
            }
        }

        panel.add(weeklyTotalLabel, BorderLayout.NORTH);
        panel.add(new JScrollPane(expensesPanel), BorderLayout.CENTER);

        return panel;
    }

    private static JPanel buildMonthlyPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        List<Subscription> subscriptions = User.getSubscriptions();
        List<SpendingPage.SpendingEntry> spending = Database.getSpending();

        if (subscriptions.isEmpty() && spending.isEmpty()) {
            JLabel emptyLabel = new JLabel("No data available.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(emptyLabel, BorderLayout.CENTER);
            return panel;
        }

        java.util.LinkedHashMap<String, Double> combined = new java.util.LinkedHashMap<>();

        for (Subscription subscription : subscriptions) {
            combined.merge(
                subscription.getTitle(),
                toMonthly(subscription.getAmount(), subscription.getFrequency()),
                Double::sum
            );
        }

        for (SpendingPage.SpendingEntry entry : spending) {
            combined.merge(entry.description, entry.amount, Double::sum);
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

        JLabel totalLabel = new JLabel(
            String.format("Total Monthly Spending: $%.2f", monthlyTotal)
        );
        totalLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        PieChartPanel chart = new PieChartPanel(labels, values, colors);

        panel.add(totalLabel, BorderLayout.NORTH);
        panel.add(chart, BorderLayout.CENTER);

        return panel;
    }

    private static double toMonthly(double amount, String frequency) {
        switch (frequency) {
            case "Weekly":
                return amount * 4.33;
            case "Bi-Weekly":
                return amount * 2.17;
            case "Monthly":
                return amount;
            case "Quarterly":
                return amount / 3.0;
            case "Annually":
                return amount / 12.0;
            default:
                return amount;
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