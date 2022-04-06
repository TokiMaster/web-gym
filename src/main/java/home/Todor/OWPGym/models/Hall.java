package home.Todor.OWPGym.models;

public class Hall {
    private int id;
    private String hallName;
    private int capacity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Hall(int id, String hallName, int capacity) {
        this.id = id;
        this.hallName = hallName;
        this.capacity = capacity;
    }
}
