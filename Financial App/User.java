import java.util.LinkedList;

public class User {
    private static String username;
    private static char[] password;
    private static LinkedList<SavingGoal> savingGoals;
    private static double balance;
    private static LinkedList<Subscription> subscriptions;


    public User(String name, char[] pass){
        username = name;
        password = pass;
        savingGoals = new LinkedList<>();
        balance = 0;
        subscriptions = new LinkedList<>();
    }

    public static String getUsername(){
        return username;
    }
    public static char[] getPassword(){
        return password;
    }
    public static double getBalance(){
        return balance;
    }

    public static void setUsername(String n){
        username = n;
    }

    public static void setPassword(char[] p){
        password = p;
    }
    public static void setBalance(double b){
        balance = b;
    }

    public static void addSavingsGoal(SavingGoal sg){
        savingGoals.add(sg);
    }

    public static void removeSavingsGoal(SavingGoal sg){
        savingGoals.remove(sg);
    }
    public static void addSubscription(Subscription s){
        subscriptions.add(s);
    }
    public static void removeSubscription(Subscription s){
        subscriptions.remove(s);
    }
    public static LinkedList<Subscription> getSubscriptions(){
        return subscriptions;
    }

    public static void initUser(LinkedList<SavingGoal> sg, double b, LinkedList<Subscription> s){
        savingGoals = sg;
        balance = b;
        subscriptions = s;
    }
}
