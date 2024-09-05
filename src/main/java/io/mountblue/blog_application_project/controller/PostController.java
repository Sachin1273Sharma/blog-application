package io.mountblue.blog_application_project.controller;

import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.service.PostService;
import io.mountblue.blog_application_project.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

    @GetMapping("/")
    public String showHomePage(Model model) {
        tagService.cleanUpTags();
        model.addAttribute("posts", postService.getAllPosts());
        return "home";
    }
    @GetMapping("/posts/{id}")
    public String showPostPage(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post";
    }
}
