//to run
//javac -cp ".;lib/sqlite-jdbc-3.53.0.0.jar;lib/flatlaf-3.7.1.jar;lib/flatlaf-intellij-themes-3.7.1.jar" *.java
//java -cp ".;lib/sqlite-jdbc-3.53.0.0.jar;lib/flatlaf-3.7.1.jar;lib/flatlaf-intellij-themes-3.7.1.jar" Main

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

//import to make the interface look better
//import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMTGitHubIJTheme;

public class Main {
    public static void main(String[] args) {
        // initialize frame
        // FlatMTGitHubIJTheme.setup(); //start the ui
        Database.init(); // initialize database connection and tables

        // might not need this one
        JFrame phone = new JFrame("Our Financial App");
        phone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        phone.setSize(500, 700);

        // initialize app
        JPanel app = new JPanel();
        app.setSize(500, 700);
        app.setVisible(true);
        phone.add(app, BorderLayout.CENTER);
        // initialize pages
        LoginPage.initLoginPage();
        HomePage.initHomePage();
        WeeklyOverviewPage.initWeeklyOverviewPage();
        MonthlyOverviewPage.initMonthlyOverviewPage();
        SubscriptionPage.initSubscriptionPage();
        IncomePage.initIncomePage();
        SpendingPage.initSpendingPage();
        TrackSavingsPage.initTrackSavings();
        AddSavingGoalsPage.initAddSavingsGoals();
        SignUpPage.initSignUpPage();

        // add pages to app
        app.add(LoginPage.getLoginArea());
        app.add(HomePage.getHomeArea());
        app.add(WeeklyOverviewPage.getWeeklyOverviewArea());
        app.add(MonthlyOverviewPage.getMonthlyOverviewArea());
        app.add(SubscriptionPage.getSubscriptionArea());
        app.add(IncomePage.getIncomeArea());
        app.add(SpendingPage.getSpendingArea());
        app.add(TrackSavingsPage.getTrackSavingsArea());
        app.add(AddSavingGoalsPage.getAddSavingsArea());
        app.add(SignUpPage.getSignUpArea());

        // make sure relevent pages are visible
        LoginPage.getLoginArea().setVisible(true);

        phone.setVisible(true);
        app.setVisible(true);
    }
}
