package home.Todor.OWPGym.service;

import home.Todor.OWPGym.models.Comment;

public interface CommentService {
    Comment acceptComment(Comment comment);
    Comment rejectComment(Comment comment);
}
