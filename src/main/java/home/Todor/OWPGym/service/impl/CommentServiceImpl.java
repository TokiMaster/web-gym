package home.Todor.OWPGym.service.impl;

import home.Todor.OWPGym.Repository.CommentRepository;
import home.Todor.OWPGym.models.Comment;
import home.Todor.OWPGym.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment acceptComment(Comment comment) {
        if(comment == null){
            return null;
        }
        comment.setDate(LocalDateTime.now());
        commentRepository.acceptComment(comment);
        return comment;
    }

    @Override
    public Comment rejectComment(Comment comment) {
        if(comment == null){
            return null;
        }
        comment.setDate(LocalDateTime.now());
        commentRepository.rejectComment(comment);
        return comment;
    }

    @Override
    public Comment addComment(Comment comment) {
        if(comment.isAnonymous()){
            comment.setAuthor(null);
        }
        if(comment.getContent().equals("") || comment.getDate().isAfter(LocalDateTime.now())){
            return null;
        }
        commentRepository.addComment(comment);
        return comment;
    }
}
