import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
public class Database {
 
    private static final String URL = "jdbc:sqlite:finance.db";
    private static Connection conn;
 
    // call once at app startup to create tables if they don't exist
    public static void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();
 
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS subscriptions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "frequency TEXT NOT NULL)"
            );
 
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS income (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "source TEXT NOT NULL," +
                "amount REAL NOT NULL)" 
            );
             stmt.execute(
                "CREATE TABLE IF NOT EXISTS spending (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "description TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "day TEXT NOT NULL)"
            );
            
            System.out.println("Database ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //subscriptions
    public static void addSubscription(String title, double amount, String frequency) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO subscriptions (title, amount, frequency) VALUES (?, ?, ?)"
            );
            ps.setString(1, title);
            ps.setDouble(2, amount);
            ps.setString(3, frequency);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving subscription: " + e.getMessage());
        }
    }
 
    public static List<Subscription> getSubscriptions() {
        List<Subscription> list = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery(
                "SELECT title, amount, frequency FROM subscriptions"
            );
            while (rs.next()) {
                list.add(new Subscription(
                    rs.getString("title"),
                    rs.getDouble("amount"),
                    rs.getString("frequency")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error loading subscriptions: " + e.getMessage());
        }
        return list;
    }
 
    public static double getTotalMonthlySubscriptions() {
        double total = 0;
        for (Subscription s : User.getSubscriptions()) {
            total += toMonthly(s.getAmount(), s.getFrequency());
        }
        return total;
    }

   public static void deleteSubscription(String title) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM subscriptions WHERE title = ?"
            );
            ps.setString(1, title);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting subscription: " + e.getMessage());
        }
    }
 
    //income 
        public static void addIncome(String source, double amount) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO income (source, amount) VALUES (?, ?)"
            );
            User.setBalance(User.getBalance() + amount);
            ps.setString(1, source);
            ps.setDouble(2, amount);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving income: " + e.getMessage());
        }
    }
 
    public static List<IncomePage.IncomeEntry> getIncome() {
        List<IncomePage.IncomeEntry> list = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery(
                "SELECT source, amount FROM income"
            );
            while (rs.next()) {
                list.add(new IncomePage.IncomeEntry(
                    rs.getString("source"),
                    rs.getDouble("amount")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error loading income: " + e.getMessage());
        }
        return list;
    }
 
    public static double getTotalIncome() {
        double total = 0;
        for (IncomePage.IncomeEntry e : getIncome()) {
            total += e.amount;
        }
        return total;
    }

    //spending
    public static void addSpending(String description, double amount, String day) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO spending (description, amount, day) VALUES (?, ?, ?)"
            );
            ps.setString(1, description);
            ps.setDouble(2, amount);
            ps.setString(3, day);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving spending: " + e.getMessage());
        }
    }
    
    public static List<SpendingPage.SpendingEntry> getSpending() {
        List<SpendingPage.SpendingEntry> list = new ArrayList<>();
        try {
            ResultSet rs = conn.createStatement().executeQuery(
                "SELECT description, amount, day FROM spending"
            );
            while (rs.next()) {
                list.add(new SpendingPage.SpendingEntry(
                    rs.getString("description"),
                    rs.getDouble("amount"),
                    rs.getString("day")
                ));
            }
        } catch (Exception e) {
            System.out.println("Error loading spending: " + e.getMessage());
        }
        return list;
    }
 
    public static double getTotalSpending() {
        double total = 0;
        for (SpendingPage.SpendingEntry e : getSpending()) {
            total += e.amount;
        }
        return total;
    }

    public static void deleteSpending(String description) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM spending WHERE description = ?"
            );
            ps.setString(1, description);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting spending: " + e.getMessage());
        }
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
}
 
