package home.Todor.OWPGym.service.impl;

import home.Todor.OWPGym.Repository.TrainingAppointmentRepository;
import home.Todor.OWPGym.models.TrainingAppointment;
import home.Todor.OWPGym.service.TrainingAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrainingAppointmentServiceImpl implements TrainingAppointmentService {

    @Autowired
    TrainingAppointmentRepository trainingAppointmentRepository;

    @Override
    public TrainingAppointment addTrainingAppointment(TrainingAppointment appointment) {
        if(appointment.getStartDate().isBefore(LocalDateTime.now())) {
            return null;
        }
        trainingAppointmentRepository.addTrainingAppointment(appointment);
        return appointment;
    }
}
