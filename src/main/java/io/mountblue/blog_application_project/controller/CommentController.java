package io.mountblue.blog_application_project.controller;

import io.mountblue.blog_application_project.entity.Comment;
import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.service.CommentService;
import io.mountblue.blog_application_project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/updateComment/{commentId}")
    public String updateComment(@PathVariable Long commentId, @RequestParam("postId") Long postId, Model model)
    {
        Comment comment=commentService.getCommentById(commentId);
        model.addAttribute("commentForm",comment);
        model.addAttribute("postId",postId);
        return "update-comment";
    }
    @PostMapping("/updateComment/{id}")
    public String updateComment(@PathVariable Long id ,@ModelAttribute("commentForm") Comment comment,@RequestParam("postId") Long postId)
    {
        Comment oldComment=commentService.getCommentById(id);
        oldComment.setName(comment.getName());
        oldComment.setEmail(comment.getEmail());
        oldComment.setComment(comment.getComment());
        oldComment.setUpdatedAt(LocalDateTime.now());
        commentService.saveComment(oldComment);
        return "redirect:/posts/"+postId;
    }
}
