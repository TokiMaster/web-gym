package home.Todor.OWPGym.models;

public class WishList {
    private int id;
    private User user;
    private Training training;

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

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public WishList(User user, Training training) {
        this.user = user;
        this.training = training;
    }

    public WishList(int id, User user, Training training) {
        this.id = id;
        this.user = user;
        this.training = training;
    }
}
