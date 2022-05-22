package home.Todor.OWPGym.Repository;

import home.Todor.OWPGym.models.ReservedAppointment;

import java.util.ArrayList;

public interface ReservedAppointmentsRepository {
    void addToReservedAppointment(ReservedAppointment reservedAppointment);
    ArrayList<ReservedAppointment> findUsersReservedTrainings(String username);
}
