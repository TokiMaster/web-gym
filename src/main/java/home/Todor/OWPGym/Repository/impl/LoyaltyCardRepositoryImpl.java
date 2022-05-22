package home.Todor.OWPGym.Repository.impl;

import home.Todor.OWPGym.Repository.LoyaltyCardRepository;
import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.models.LoyaltyCard;
import home.Todor.OWPGym.models.Status;
import home.Todor.OWPGym.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class LoyaltyCardRepositoryImpl implements LoyaltyCardRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    private class LoyaltyCardRowCallbackHandler implements RowCallbackHandler{

        private ArrayList<LoyaltyCard> loyaltyCards = new ArrayList<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;

            int id = rs.getInt(index++);
            User user = userRepository.findOne(rs.getString(index++));
            int points = rs.getInt(index++);
            Status status = Status.valueOf(rs.getString(index++));

            LoyaltyCard loyaltyCard = new LoyaltyCard(id, user, points, status);
            loyaltyCards.add(loyaltyCard);
        }
    }

    @Override
    public LoyaltyCard findOne(int id) {
        String sql = "select * from LoyaltyCard where id = ?";
        LoyaltyCardRowCallbackHandler rowCallbackHandler = new LoyaltyCardRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, id);
        if(rowCallbackHandler.loyaltyCards.isEmpty()){
            return null;
        }
        return rowCallbackHandler.loyaltyCards.get(0);
    }

    @Override
    public ArrayList<LoyaltyCard> findAll() {
        String sql = "select * from LoyaltyCard where status = 'ON_HOLD'";
        LoyaltyCardRowCallbackHandler rowCallbackHandler = new LoyaltyCardRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);
        if(rowCallbackHandler.loyaltyCards.isEmpty()){
            return null;
        }
        return rowCallbackHandler.loyaltyCards;
    }

    @Override
    public LoyaltyCard findOneByUsername(String username){
        String sql = "select * from LoyaltyCard where user = ? and status = 'ACCEPTED'";
        LoyaltyCardRowCallbackHandler rowCallbackHandler = new LoyaltyCardRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, username);
        if(rowCallbackHandler.loyaltyCards.isEmpty()){
            return null;
        }
        return rowCallbackHandler.loyaltyCards.get(0);
    }

    @Override
    public LoyaltyCard findOneAccepted(LoyaltyCard loyaltyCard) {
        String sql = "select * from LoyaltyCard where user = ? and status = 'ON_HOLD'";
        LoyaltyCardRowCallbackHandler rowCallbackHandler = new LoyaltyCardRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, loyaltyCard.getUser().getUsername());
        if(rowCallbackHandler.loyaltyCards.isEmpty()){
            return null;
        }
        return rowCallbackHandler.loyaltyCards.get(0);
    }

    @Override
    public void requestLoyaltyCard(LoyaltyCard loyaltyCard) {
        String sql = "insert into LoyaltyCard(user) value(?)";
        jdbcTemplate.update(sql, loyaltyCard.getUser().getUsername());
    }

    @Override
    public void acceptLoyaltyCard(LoyaltyCard loyaltyCard) {
        String sql = "update LoyaltyCard set status = 'ACCEPTED', points = 10 where id = ?";
        jdbcTemplate.update(sql, loyaltyCard.getId());
    }

    @Override
    public void rejectLoyaltyCard(LoyaltyCard loyaltyCard) {
        String sql = "update LoyaltyCard set status = 'REJECTED' where id = ?";
        jdbcTemplate.update(sql, loyaltyCard.getId());
    }

    @Override
    public void addPoints(LoyaltyCard loyaltyCard) {
        String sql = "update LoyaltyCard set points = ? where id = ?";
        jdbcTemplate.update(sql, loyaltyCard.getPoints(), loyaltyCard.getId());
    }
}
