package io.mountblue.blog_application_project.controller;

import io.mountblue.blog_application_project.entity.Comment;
import io.mountblue.blog_application_project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/comment/{postId}")
    public String addNewComment(@PathVariable Long postId, @ModelAttribute("commentForm") Comment comment) {
        commentService.addNewComment(postId, comment);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/updateComment/{commentId}")
    public String updateComment(@PathVariable Long commentId, @RequestParam("postId") Long postId, Model model) {
        Comment comment = commentService.getCommentById(commentId);
        model.addAttribute("commentForm", comment);
        model.addAttribute("postId", postId);
        return "update-comment";
    }

    @PostMapping("/updateComment/{id}")
    public String updateComment(@PathVariable Long id, @ModelAttribute("commentForm") Comment comment, @RequestParam("postId") Long postId) {
        commentService.updateComment(id, comment);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/deleteComment/{commentId}")
    public String deleteComment(@PathVariable Long commentId, @RequestParam("postId") Long postId) {
        commentService.deleteCommentById(commentId);
        return "redirect:/posts/" + postId;
    }
}
