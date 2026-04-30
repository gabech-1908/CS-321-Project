import java.text.SimpleDateFormat;
import java.time.LocalDate;

class SpendingEntry {
    private String description;
    private double amount;
    private LocalDate day;

    public SpendingEntry(String description, double amount, LocalDate day) {
        this.description = description;
        this.amount = amount;
        this.day = day;
    }

    public LocalDate getRawDate() {
        return day;
    }
    public String getDate(){
        String day = new SimpleDateFormat("dd/mm/yyyy").format(this.day);
        return day;
    }   
    public void setDate(String date){
        day = LocalDate.parse(date);
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}