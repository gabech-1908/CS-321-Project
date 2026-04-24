import java.sql.Date;

public class SavingGoal {
    private float goalAmount;
    private String name;
    private Date deadline;
    private float amountSaved;

    public SavingGoal(float ga, String n){
        goalAmount = ga;
        name = n;
    }

    public SavingGoal(float ga, String n, Date dl){
        goalAmount = ga;
        name = n;
        deadline = dl;
    }

    public float goalProgress(){
        return amountSaved/goalAmount;
    }
    

    public String getName(){
        return name;
    }
    
    public float getGoalAmount(){
        return goalAmount;
    }

    public Date getDeadline(){
        return deadline;
    }
}
