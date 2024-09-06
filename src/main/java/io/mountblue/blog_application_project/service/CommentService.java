package io.mountblue.blog_application_project.service;

import io.mountblue.blog_application_project.entity.Comment;
import io.mountblue.blog_application_project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public void saveComment(Comment oldComment) {
        commentRepository.save(oldComment);
    }
    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
