import java.util.LinkedList;

public class User {
    private static String username;
    private static char[] password;
    private static LinkedList<SavingGoal> savingGoals;
    private static double balance;

    public User(String name, char[] pass){
        username = name;
        password = pass;
        savingGoals = new LinkedList<>();
        balance = 0;
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

    public static void initUser(LinkedList<SavingGoal> sg, double b){
        savingGoals = sg;
        balance = b;
    }
}
