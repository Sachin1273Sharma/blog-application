package io.mountblue.blog_application_project.service;

import io.mountblue.blog_application_project.entity.Comment;
import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public void updateComment(Long id, Comment comment) {

        Comment oldComment = getCommentById(id);
        oldComment.setName(comment.getName());
        oldComment.setEmail(comment.getEmail());
        oldComment.setComment(comment.getComment());
        oldComment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(oldComment);
    }

    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void addNewComment(Long postId, Comment comment) {
        Post post = postService.getPostById(postId);
        comment.setPost(post);
        LocalDateTime localDateTime = LocalDateTime.now();
        comment.setCreatedAt(localDateTime);
        comment.setUpdatedAt(localDateTime);
        post.getComments().add(comment);
        postService.savePost(post);
    }
}
