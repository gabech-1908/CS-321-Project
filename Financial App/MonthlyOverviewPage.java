import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MonthlyOverviewPage {
    private static JPanel monthlyOverviewArea;
    private static JTextArea monthRangeArea;
    private static JTextArea monthlyTotalArea;

    private static JButton homeButton;
    private static JButton signOutButton;

    public static void initMonthlyOverviewPage() {
        monthlyOverviewArea = new JPanel();
        // Stack title, summary, and chart from top to bottom.
        monthlyOverviewArea.setLayout(new BoxLayout(monthlyOverviewArea, BoxLayout.Y_AXIS));

        Font bigFont = new Font("SansSerif", Font.PLAIN, 18);

        homeButton = new JButton("Home");
        homeButton.setFont(bigFont);
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hide this page before returning to Home.
                switchOutOfMonthlyOverview();
                HomePage.switchToHome();
            }
        });

        signOutButton = new JButton("Sign Out");
        signOutButton.setFont(bigFont);
        signOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Sign out flow: hide monthly page and show login page.
                switchOutOfMonthlyOverview();
                HomePage.switchOutOfHome();
                LoginPage.switchToLogin();
            }
        });

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.add(homeButton, BorderLayout.WEST);
        topBar.add(signOutButton, BorderLayout.EAST);

        monthRangeArea = new JTextArea("Month: April 2026");
        monthRangeArea.setFont(bigFont);
        monthRangeArea.setEditable(false);

        monthlyTotalArea = new JTextArea("Monthly Spending Total: $1,920.00");
        monthlyTotalArea.setFont(bigFont);
        monthlyTotalArea.setEditable(false);

        // Sample category totals for a month. Replace with real transaction aggregates later.
        String[] categories = { "Food", "Transport", "Rent", "Shopping", "Other" };
        double[] values = { 420.0, 180.0, 950.0, 250.0, 120.0 };
        Color[] colors = {
            new Color(239, 83, 80),
            new Color(66, 165, 245),
            new Color(102, 187, 106),
            new Color(255, 202, 40),
            new Color(171, 71, 188)
        };

        PieChartPanel monthlyTransactionsChart = new PieChartPanel(categories, values, colors);

        // Compose the page sections in display order.
        monthlyOverviewArea.add(topBar);
        monthlyOverviewArea.add(monthRangeArea);
        monthlyOverviewArea.add(monthlyTotalArea);
        monthlyOverviewArea.add(monthlyTransactionsChart);

        monthlyOverviewArea.setVisible(false);
    }

    public static void switchToMonthlyOverview() {
        if (monthlyOverviewArea != null) {
            monthlyOverviewArea.setVisible(true);
        }
    }

    public static void switchOutOfMonthlyOverview() {
        if (monthlyOverviewArea != null) {
            monthlyOverviewArea.setVisible(false);
        }
    }

    public static JPanel getMonthlyOverviewArea() {
        return monthlyOverviewArea;
    }
}