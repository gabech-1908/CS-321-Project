public class Subscription {
    private String title;
    private double amount;
    private String frequency;

    public Subscription(String title, double amount, String frequency) {
        this.title = title;
        this.amount = amount;
        this.frequency = frequency;
    }

    public String getTitle(){
        return title;
    }
    public double getAmount(){
        return amount;
    }
    public String getFrequency(){
        return frequency;
    }
}
