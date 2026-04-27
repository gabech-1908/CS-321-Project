import java.sql.Date;

public class SavingGoal {
    private double goalAmount;
    private String name;
    private Date deadline;
    private double amountSaved;

    public SavingGoal(float ga, String n){
        goalAmount = ga;
        name = n;
    }

    public SavingGoal(float ga, String n, Date dl){
        goalAmount = ga;
        name = n;
        deadline = dl;
    }

    public double goalProgress(){
        return amountSaved/goalAmount;
    }

    public String getName(){
        return name;
    }
    
    public double getGoalAmount(){
        return goalAmount;
    }

    public Date getDeadline(){
        return deadline;
    }

    public boolean goalMet(){
        return goalProgress() >= 1;
    }
}
