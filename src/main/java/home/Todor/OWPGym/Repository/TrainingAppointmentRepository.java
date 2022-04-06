package home.Todor.OWPGym.Repository;

import home.Todor.OWPGym.models.TrainingAppointment;

import java.util.ArrayList;

public interface TrainingAppointmentRepository {
    TrainingAppointment findOne(int id);
    ArrayList<TrainingAppointment> findAll();
    void addTrainingAppointment(TrainingAppointment appointment);
}
