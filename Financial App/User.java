import java.util.LinkedList;

public class User {
    private static int userId = -1;
    private static String username;
    private static char[] password;
    private static LinkedList<SavingGoal> savingGoals;
    private static double balance;
    private static LinkedList<Subscription> subscriptions;
    private static LinkedList<SpendingEntry> spendingEntries;


    public User(String name, char[] pass){
        username = name;
        password = pass;
        savingGoals = new LinkedList<>();
        balance = 0;
        subscriptions = new LinkedList<>();
        spendingEntries = new LinkedList<>();
    }

    public static int getUserId() { return userId; }
    public static void setUserId(int id) { userId = id; }

    public static String getUsername(){
        return username;
    }
    public static char[] getPassword(){
        return password;
    }
    public static double getBalance(){
        return balance;
    }
    public static LinkedList<SavingGoal> getSavingGoals(){
        return savingGoals;
    }
    public static LinkedList<SpendingEntry> getSpendingEntries(){
        return spendingEntries;
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

    public static void removeSavingsGoal(String name){
        for (SavingGoal sg : savingGoals) {
            if (sg.getName().equals(name)) {
                savingGoals.remove(sg);
                return;
            }
        }
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
    public static void addSpendingEntry(SpendingEntry se){
        // sort by date
        // most recent at front of list
        for (int i = 0; i < spendingEntries.size(); i++){
            if (se.getRawDate().isAfter(spendingEntries.get(i).getRawDate())){
                spendingEntries.add(i, se);
                return;
            }
        }   
        spendingEntries.add(se);
    }

    public static void initUser(LinkedList<SavingGoal> sg, double b, LinkedList<Subscription> s, LinkedList<SpendingEntry> se){
        savingGoals = sg;
        balance = b;
        subscriptions = s;
        spendingEntries = se;
    }
}
