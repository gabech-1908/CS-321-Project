import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TrackSavingsPage {
    private static JPanel trackSavingsArea;
    private static JPanel listPanel;

    private static JLabel trackSavingsLabel;
    private static JButton addSavings;

    public static void initTrackSavings() {
        trackSavingsArea = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("Home");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchFromTrackSavings();
                HomePage.switchToHome();
            }
        });

        JButton signoutButton = new JButton("Sign Out");
        signoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchFromTrackSavings();
                LoginPage.switchToLogin();
            }
        });

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(signoutButton, BorderLayout.EAST);

        JPanel centerPanel = new JPanel(new BorderLayout());

        trackSavingsLabel = new JLabel("Savings Goals", JLabel.CENTER);

        addSavings = new JButton("Add Goals");
        addSavings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchFromTrackSavings();
                AddSavingGoalsPage.switchToAddSavingGoals();
            }
        });

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(trackSavingsLabel, BorderLayout.CENTER);
        headerPanel.add(addSavings, BorderLayout.EAST);

        listPanel = new JPanel(new GridLayout(0, 3, 10, 10));

        centerPanel.add(headerPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(listPanel), BorderLayout.CENTER);

        trackSavingsArea.add(topPanel, BorderLayout.NORTH);
        trackSavingsArea.add(centerPanel, BorderLayout.CENTER);

        trackSavingsArea.setVisible(false);
        refreshTrackSavings();
    }

    public static void swtichToTrackSavings() {
        refreshTrackSavings();
        trackSavingsArea.setVisible(true);
    }

    public static void switchFromTrackSavings() {
        trackSavingsArea.setVisible(false);
    }

    public static JPanel getTrackSavingsArea() {
        return trackSavingsArea;
    }

    public static void refreshTrackSavings() {
        if (listPanel == null) {
            return;
        }

        listPanel.removeAll();

        listPanel.add(new JLabel("Name"));
        listPanel.add(new JLabel("Amount"));
        listPanel.add(new JLabel("Action"));

        if (User.getSavingGoals() != null) {
            for (SavingGoal sg : User.getSavingGoals()) {
                JLabel goalName = new JLabel(sg.getName());
                JLabel goalAmount = new JLabel(String.format("$%.2f", sg.getGoalAmount()));

                JButton removeGoal = new JButton("Remove");
                removeGoal.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        User.removeSavingsGoal(sg.getName());
                        refreshTrackSavings();
                    }
                });

                listPanel.add(goalName);
                listPanel.add(goalAmount);
                listPanel.add(removeGoal);
            }
        }

        listPanel.revalidate();
        listPanel.repaint();

        if (trackSavingsArea != null) {
            trackSavingsArea.revalidate();
            trackSavingsArea.repaint();
        }
    }
}