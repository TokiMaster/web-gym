package home.Todor.OWPGym.Repository;

import home.Todor.OWPGym.models.Comment;

import java.util.ArrayList;

public interface CommentRepository {
    Comment findOne(int id);
    ArrayList<Comment> findAll();
    void acceptComment(Comment comment);
    void rejectComment(Comment comment);
}
