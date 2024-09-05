package io.mountblue.blog_application_project.controller;

import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.service.PostService;
import io.mountblue.blog_application_project.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class PostController {
    @Autowired
    PostService postService;
    @Autowired
    TagService tagService;
    @GetMapping("/posts/new")
    public String showCreatePostPage(Model model) {
        model.addAttribute("post", new Post());
        return "create-post";
    }
    @PostMapping("/posts/save")
    public String savePost(@ModelAttribute("post") Post post, @RequestParam("tagsInput") String tags) {
        post.setCreatedAt(LocalDateTime.now());
        post.setPublishedAt(LocalDateTime.now());
        post.setIsPublished(true);
        postService.savePost(post);
        tagService.addTagsToPost(post.getId(), tags, false);
        return "redirect:/";
    }
}
