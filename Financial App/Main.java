import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main{


    public static void main(String[] args) {
        //initialize frame
        //might not need this one
        JFrame phone = new JFrame("Our Financial App");
        phone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        phone.setSize(500, 700);

        
        //initialize app
        JPanel app = new JPanel();
        app.setSize(350, 700);
        app.setVisible(true);
        phone.add(app, BorderLayout.CENTER);
        //initialize pages
        LoginPage.initLoginPage();
        HomePage.initHomePage();
        WeeklyOverviewPage.initWeeklyOverviewPage();
        MonthlyOverviewPage.initMonthlyOverviewPage();

        //add pages to app
        app.add(LoginPage.getLoginArea());
        app.add(HomePage.getHomeArea());
        app.add(WeeklyOverviewPage.getWeeklyOverviewArea());
        app.add(MonthlyOverviewPage.getMonthlyOverviewArea());

        //make sure relevent pages are visible
        LoginPage.getLoginArea().setVisible(true);
        
        phone.setVisible(true);
        app.setVisible(true);
    }
}
