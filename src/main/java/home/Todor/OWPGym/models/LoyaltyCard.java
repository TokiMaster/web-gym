package home.Todor.OWPGym.models;

public class LoyaltyCard {
    private int id;
    private User user;
    private int points;
    private Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LoyaltyCard(User user){
        this.user = user;
    }

//    public LoyaltyCard(User user, int points, Status status) {
//        this.user = user;
//        this.points = points;
//        this.status = status;
//    }

    public LoyaltyCard(int id, User user, int points, Status status) {
        this.id = id;
        this.user = user;
        this.points = points;
        this.status = status;
    }
}
