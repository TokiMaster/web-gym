package home.Todor.OWPGym.Repository.impl;

import home.Todor.OWPGym.Repository.CommentRepository;
import home.Todor.OWPGym.Repository.TrainingRepository;
import home.Todor.OWPGym.Repository.UserRepository;
import home.Todor.OWPGym.models.Comment;
import home.Todor.OWPGym.models.Status;
import home.Todor.OWPGym.models.Training;
import home.Todor.OWPGym.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrainingRepository trainingRepository;

    private class CommentRowCallbackHandler implements RowCallbackHandler{

        private ArrayList<Comment> comments = new ArrayList<>();

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            int index = 1;

            int id = rs.getInt(index++);
            String content = rs.getString(index++);
            int rating = rs.getInt(index++);
            User author = userRepository.findOne(rs.getString(index++));
            Training training = trainingRepository.findOne(rs.getInt(index++));
            Status status = Status.valueOf(rs.getString(index++));
            Boolean anonymous = rs.getBoolean(index++);

            Comment comment = new Comment(id, content, rating, author, training, status, anonymous);
            comments.add(comment);
        }
    }

    @Override
    public Comment findOne(int id) {
        String sql = "select * from Comments where id = ?";
        CommentRowCallbackHandler rowCallbackHandler = new CommentRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, id);
        if(rowCallbackHandler.comments.isEmpty()){
            return null;
        }
        return rowCallbackHandler.comments.get(0);
    }

    @Override
    public ArrayList<Comment> findAll() {
        String sql = "select * from Comments where status = 'ON_HOLD'";
        CommentRowCallbackHandler rowCallbackHandler = new CommentRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);
        if (rowCallbackHandler.comments.isEmpty()) {
            return null;
        }
        return rowCallbackHandler.comments;
    }

    @Override
    public void acceptComment(Comment comment) {
        String sql = "update Comments set status = 'ACCEPTED' where id = ?";
        jdbcTemplate.update(sql, comment.getId());
    }

    @Override
    public void rejectComment(Comment comment) {
        String sql = "update Comments set status = 'REJECTED' where id = ?";
        jdbcTemplate.update(sql, comment.getId());
    }
}
