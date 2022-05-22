package home.Todor.OWPGym.Repository.impl;

import home.Todor.OWPGym.Repository.ReservedAppointmentsRepository;
import home.Todor.OWPGym.Repository.TrainingRepository;
import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Repository
public class ReservedAppointmentsRepositoryImpl implements ReservedAppointmentsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    UserRepository userRepository;

    private class ReservedAppointmentRowCallbackHandler implements RowCallbackHandler {

        private ArrayList<ReservedAppointment> appointments = new ArrayList<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;

            int id = rs.getInt(index++);
            User user = userRepository.findOne(rs.getString(index++));
            Training training = trainingRepository.findOne(rs.getInt(index++));

            ReservedAppointment wishlist = new ReservedAppointment(id, user, training);
            appointments.add(wishlist);
        }
    }

    @Override
    public void addToReservedAppointment(ReservedAppointment reservedAppointment) {
        String sql = "insert into ReservedAppointments(user, training) value (?, ?)";
        jdbcTemplate.update(sql, reservedAppointment.getUser().getUsername(), reservedAppointment.getTraining().getId());
    }

    @Override
    public ArrayList<ReservedAppointment> findUsersReservedTrainings(String username) {
        String sql = "select * from ReservedAppointments where user = ?";
        ReservedAppointmentRowCallbackHandler rowCallbackHandler = new ReservedAppointmentRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, username);
        if(rowCallbackHandler.appointments.isEmpty()) {
            return null;
        }
        return rowCallbackHandler.appointments;
    }

}
