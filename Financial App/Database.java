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

    public static void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
            Statement stmt = conn.createStatement();

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL)"
            );

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS subscriptions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "title TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "frequency TEXT NOT NULL)"
            );

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS income (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "source TEXT NOT NULL," +
                "amount REAL NOT NULL)"
            );

            stmt.execute(
                "CREATE TABLE IF NOT EXISTS spending (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "description TEXT NOT NULL," +
                "amount REAL NOT NULL," +
                "day TEXT NOT NULL)"
            );

            // handle existing databases without user_id columns
            tryAlter("ALTER TABLE subscriptions ADD COLUMN user_id INTEGER NOT NULL DEFAULT 0");
            tryAlter("ALTER TABLE income ADD COLUMN user_id INTEGER NOT NULL DEFAULT 0");
            tryAlter("ALTER TABLE spending ADD COLUMN user_id INTEGER NOT NULL DEFAULT 0");

            // seed default demo account
            stmt.execute(
                "INSERT OR IGNORE INTO users (username, password) VALUES ('admin', 'admin')"
            );

            System.out.println("Database ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void tryAlter(String sql) {
        try {
            conn.createStatement().execute(sql);
        } catch (Exception e) {
            // column already exists, ignore
        }
    }

    // subscriptions
    public static void addSubscription(String title, double amount, String frequency) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO subscriptions (user_id, title, amount, frequency) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, User.getUserId());
            ps.setString(2, title);
            ps.setDouble(3, amount);
            ps.setString(4, frequency);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving subscription: " + e.getMessage());
        }
    }

    public static List<Subscription> getSubscriptions() {
        List<Subscription> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT title, amount, frequency FROM subscriptions WHERE user_id = ?"
            );
            ps.setInt(1, User.getUserId());
            ResultSet rs = ps.executeQuery();
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
                "DELETE FROM subscriptions WHERE title = ? AND user_id = ?"
            );
            ps.setString(1, title);
            ps.setInt(2, User.getUserId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error deleting subscription: " + e.getMessage());
        }
    }

    // income
    public static void addIncome(String source, double amount) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO income (user_id, source, amount) VALUES (?, ?, ?)"
            );
            User.setBalance(User.getBalance() + amount);
            ps.setInt(1, User.getUserId());
            ps.setString(2, source);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving income: " + e.getMessage());
        }
    }

    public static List<IncomePage.IncomeEntry> getIncome() {
        List<IncomePage.IncomeEntry> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT source, amount FROM income WHERE user_id = ?"
            );
            ps.setInt(1, User.getUserId());
            ResultSet rs = ps.executeQuery();
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

    // spending
    public static void addSpending(String description, double amount, String day) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO spending (user_id, description, amount, day) VALUES (?, ?, ?, ?)"
            );
            ps.setInt(1, User.getUserId());
            ps.setString(2, description);
            ps.setDouble(3, amount);
            ps.setString(4, day);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error saving spending: " + e.getMessage());
        }
    }

    public static List<SpendingPage.SpendingEntry> getSpending() {
        List<SpendingPage.SpendingEntry> list = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT description, amount, day FROM spending WHERE user_id = ?"
            );
            ps.setInt(1, User.getUserId());
            ResultSet rs = ps.executeQuery();
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
                "DELETE FROM spending WHERE description = ? AND user_id = ?"
            );
            ps.setString(1, description);
            ps.setInt(2, User.getUserId());
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

    // users
    public static boolean addUser(String username, String password) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (username, password) VALUES (?, ?)"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean userExists(String username) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM users WHERE username = ?"
            );
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkLogin(String username, String password) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id FROM users WHERE username = ? AND password = ?"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User.setUserId(rs.getInt("id"));
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Error closing database: " + e.getMessage());
        }
    }
}
