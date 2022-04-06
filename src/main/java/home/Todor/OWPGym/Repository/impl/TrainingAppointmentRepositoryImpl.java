package home.Todor.OWPGym.Repository.impl;

import home.Todor.OWPGym.Repository.HallRepository;
import home.Todor.OWPGym.Repository.TrainingAppointmentRepository;
import home.Todor.OWPGym.Repository.TrainingRepository;
import home.Todor.OWPGym.models.Hall;
import home.Todor.OWPGym.models.Training;
import home.Todor.OWPGym.models.TrainingAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository
public class TrainingAppointmentRepositoryImpl implements TrainingAppointmentRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    HallRepository hallRepository;

    private class TrainingAppointmentRowCallbackHandler implements RowCallbackHandler {

        private ArrayList<TrainingAppointment> trainingAppointments = new ArrayList<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;

            int id = rs.getInt(index++);
            Hall hall = hallRepository.findOne(rs.getInt(index++));
            Training training = trainingRepository.findOne(rs.getInt(index++));
            LocalDateTime startDate = rs.getTimestamp(index++).toLocalDateTime();

            TrainingAppointment trainingAppointment = new TrainingAppointment(id, hall, training, startDate);
            trainingAppointments.add(trainingAppointment);
        }
    }

    @Override
    public TrainingAppointment findOne(int id) {
        String sql = "select * from TrainingAppointment where id = ?";
        TrainingAppointmentRowCallbackHandler rowCallbackHandler = new TrainingAppointmentRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, id);
        if(rowCallbackHandler.trainingAppointments.isEmpty()){
            return null;
        }
        return rowCallbackHandler.trainingAppointments.get(0);
    }

    @Override
    public ArrayList<TrainingAppointment> findAll() {
        String sql = "select * from TrainingAppointment";
        TrainingAppointmentRowCallbackHandler rowCallbackHandler = new TrainingAppointmentRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);
        return  rowCallbackHandler.trainingAppointments;
    }

    @Override
    public void addTrainingAppointment(TrainingAppointment appointment) {
        String sql = "insert into TrainingAppointment (hall, training, startDate) values (?,?,?)";
        jdbcTemplate.update(sql, appointment.getHall().getId(),
                appointment.getTraining().getId(), appointment.getStartDate());
    }
}
