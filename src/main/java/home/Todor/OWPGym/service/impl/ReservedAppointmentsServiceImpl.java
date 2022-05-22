package home.Todor.OWPGym.service.impl;

import home.Todor.OWPGym.Repository.ReservedAppointmentsRepository;
import home.Todor.OWPGym.models.ReservedAppointment;
import home.Todor.OWPGym.service.ReservedAppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservedAppointmentsServiceImpl implements ReservedAppointmentsService {

    @Autowired
    ReservedAppointmentsRepository reservedAppointmentsRepository;

    @Override
    public ReservedAppointment addToReservedAppointment(ReservedAppointment reservedAppointment) {
        reservedAppointmentsRepository.addToReservedAppointment(reservedAppointment);
        return reservedAppointment;
    }
}
