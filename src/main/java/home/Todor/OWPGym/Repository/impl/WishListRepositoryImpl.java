package home.Todor.OWPGym.Repository.impl;

import home.Todor.OWPGym.Repository.TrainingRepository;
import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.Repository.WishListRepository;
import home.Todor.OWPGym.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class WishListRepositoryImpl implements WishListRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    UserRepository userRepository;

    private class WishListRowCallbackHandler implements RowCallbackHandler {

        private ArrayList<WishList> wishLists = new ArrayList<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;

            int id = rs.getInt(index++);
            User user = userRepository.findOne(rs.getString(index++));
            Training training = trainingRepository.findOne(rs.getInt(index++));

            WishList wishlist = new WishList(id, user, training);
            wishLists.add(wishlist);
        }
    }

    @Override
    public WishList findOne(int id) {
        String sql = "select * from WishList where id = ?";
        WishListRowCallbackHandler rowCallbackHandler = new WishListRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, id);
        if(rowCallbackHandler.wishLists.isEmpty()) {
            return null;
        }
        return rowCallbackHandler.wishLists.get(0);
    }

    @Override
    public ArrayList<WishList> findAll() {
        String sql = "select * from WishList";
        WishListRowCallbackHandler rowCallbackHandler = new WishListRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);
        return  rowCallbackHandler.wishLists;
    }

    @Override
    public void addToWishList(WishList wishList) {
        String sql = "insert into WishList(user, training) value (?, ?)";
        jdbcTemplate.update(sql, wishList.getUser().getUsername(), wishList.getTraining().getId());
    }

    @Override
    public void deleteFromWishList(WishList wishList) {
        String sql = "delete from WishList where id = ?";
        jdbcTemplate.update(sql, wishList.getId());
    }

    @Override
    public ArrayList<WishList> findUsersWishList(String username) {
        String sql = "select * from WishList where user = ?";
        WishListRowCallbackHandler rowCallbackHandler = new WishListRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, username);
        if(rowCallbackHandler.wishLists.isEmpty()) {
            return null;
        }
        return rowCallbackHandler.wishLists;
    }


}
