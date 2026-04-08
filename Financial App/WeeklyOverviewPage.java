import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WeeklyOverviewPage {
    private static JPanel weeklyOverviewArea;
    private static JPanel displayExpensesArea;

    private static JTextArea dateArea;
    private static JTextArea weeklyAvgArea;

    private static double weeklyAvg;

    public static void initWeeklyOverviewPage() {

        weeklyOverviewArea = new JPanel();
        displayExpensesArea = new JPanel();

        // hard code weekly avg
        weeklyAvg = 65.00;
        dateArea = new JTextArea("Week: 4/5/2026 - 4/11/2026");
        weeklyAvgArea = new JTextArea("Weekly Average Spending: $" + weeklyAvg);

        GroupLayout layout = new GroupLayout(displayExpensesArea);
        JTextArea mon = new JTextArea("Monday");
        JTextArea tue = new JTextArea("Tuesday");
        JTextArea wed = new JTextArea("Wednesday");
        JTextArea thu = new JTextArea("Thursday");
        JTextArea fri = new JTextArea("Friday");
        JTextArea sat = new JTextArea("Saturday");
        JTextArea sun = new JTextArea("Sunday");

        displayExpensesArea.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(mon)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(tue)
                                .addComponent(wed))
                        .addComponent(thu));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(mon)
                                .addComponent(tue)
                                .addComponent(wed))
                        .addComponent(thu));

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
    }

    public static JPanel getWeeklyOverviewArea() {
        return weeklyOverviewArea;
    }
}
