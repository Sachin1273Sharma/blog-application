package io.mountblue.blog_application_project.controller;

import io.mountblue.blog_application_project.entity.Comment;
import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.service.CommentService;
import io.mountblue.blog_application_project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
@Controller
public class CommentController {
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @PostMapping("/comment/{postId}")
    public String addNewComment(@PathVariable Long postId, @ModelAttribute("commentForm") Comment comment)
    {
        Post post=postService.getPostById(postId);
        comment.setPost(post);
        LocalDateTime localDateTime=LocalDateTime.now();
        comment.setCreatedAt(localDateTime);
        comment.setUpdatedAt(localDateTime);
        post.getComments().add(comment);
        postService.savePost(post);
        return "redirect:/posts/"+postId;
    }
}
