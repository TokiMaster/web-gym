package home.Todor.OWPGym.models;

public class Hall {
    private String hallName;
    private String capacity;

    public String gethallName() {
        return hallName;
    }

    public void sethallName(String hallName) {
        this.hallName = hallName;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Hall(){
        
    }

    public Hall(String hallName, String capacity) {
        this.hallName = hallName;
        this.capacity = capacity;
    }
}
