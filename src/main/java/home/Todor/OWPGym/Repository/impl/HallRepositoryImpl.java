package home.Todor.OWPGym.Repository.impl;

import home.Todor.OWPGym.Repository.HallRepository;
import home.Todor.OWPGym.models.Hall;
import home.Todor.OWPGym.models.TypeOfTraining;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class HallRepositoryImpl implements HallRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private class HallRowCallbackHandler implements RowCallbackHandler {

        private ArrayList<Hall> halls = new ArrayList<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;

            String hallName = rs.getString(index++);
            int capacity = rs.getInt(index++);

            Hall hall = new Hall(hallName, capacity);
            halls.add(hall);
        }
    }

    @Override
    public Hall findOne(String hallName) {
        String sql = "select * from Hall where hallName = ?";
        HallRowCallbackHandler hallRowCallbackHandler = new HallRowCallbackHandler();
        jdbcTemplate.query(sql, hallRowCallbackHandler, hallName);
        if(hallRowCallbackHandler.halls.isEmpty()) {
            return null;
        }
        return hallRowCallbackHandler.halls.get(0);
    }

    @Override
    public ArrayList<Hall> findAll() {
        String sql = "select * from Hall";
        HallRowCallbackHandler hallRowCallbackHandler = new HallRowCallbackHandler();
        jdbcTemplate.query(sql, hallRowCallbackHandler);
        return hallRowCallbackHandler.halls;
    }

    @Override
    public void addHall(Hall hall) {
        String sql = "insert into Hall(hallName, capacity) values (?,?)";
        jdbcTemplate.update(sql, hall.getHallName(), hall.getCapacity());
    }
}
