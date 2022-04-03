package home.Todor.OWPGym.models;

public class Hall {

    private String hallName;
    private int capacity;

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Hall(String hallName, int capacity) {
        this.hallName = hallName;
        this.capacity = capacity;
    }
}
