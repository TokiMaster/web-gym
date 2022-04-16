package home.Todor.OWPGym.Repository;

import home.Todor.OWPGym.models.Comment;
import home.Todor.OWPGym.models.Training;

import java.util.ArrayList;

public interface CommentRepository {
    Comment findOne(int id);
    ArrayList<Comment> findAll();
    ArrayList<Comment> findAllAcceptedComments(Training training);
    void acceptComment(Comment comment);
    void rejectComment(Comment comment);
    void addComment(Comment comment);
}
