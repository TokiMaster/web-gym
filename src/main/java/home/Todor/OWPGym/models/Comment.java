package home.Todor.OWPGym.models;

public class Comment {

    private int id;
    private String content;
    private int rating;
    private User author;
    private Training training;
    private Status status;
    private boolean anonymous;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public Comment(String content, int rating, User author, Training training, boolean anonymous) {
        this.content = content;
        this.rating = rating;
        this.author = author;
        this.training = training;
        this.anonymous = anonymous;
    }

    public Comment(int id, String content, int rating, User author, Training training, Status status, boolean anonymous) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.author = author;
        this.training = training;
        this.status = status;
        this.anonymous = anonymous;
    }
}
