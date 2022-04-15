package home.Todor.OWPGym.service.impl;

import home.Todor.OWPGym.Repository.CommentRepository;
import home.Todor.OWPGym.models.Comment;
import home.Todor.OWPGym.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment acceptComment(Comment comment) {
        if(comment == null){
            return null;
        }
        commentRepository.acceptComment(comment);
        return comment;
    }

    @Override
    public Comment rejectComment(Comment comment) {
        if(comment == null){
            return null;
        }
        commentRepository.rejectComment(comment);
        return comment;
    }
}
