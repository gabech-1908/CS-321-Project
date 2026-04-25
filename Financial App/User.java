import java.util.LinkedList;

public class User {
    private static String username;
    private static char[] password;
    private static LinkedList<SavingGoal> savingGoals;

    public User(String name, char[] pass){
        username = name;
        password = pass;
        savingGoals = new LinkedList<>();
    }

    public static String getUsername(){
        return username;
    }
    public static char[] getPassword(){
        return password;
    }

    public static void addSavingsGoal(SavingGoal sg){
        savingGoals.add(sg);
    }
}
