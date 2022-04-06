package home.Todor.OWPGym.models;

import java.time.LocalDateTime;

public class TrainingAppointment {
    private int id;
    private Hall hall;
    private Training training;
    private LocalDateTime startDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public TrainingAppointment(Hall hall, Training training, LocalDateTime startDate) {
        this.hall = hall;
        this.training = training;
        this.startDate = startDate;
    }

    public TrainingAppointment(int id, Hall hall, Training training, LocalDateTime startDate) {
        this.id = id;
        this.hall = hall;
        this.training = training;
        this.startDate = startDate;
    }
}
