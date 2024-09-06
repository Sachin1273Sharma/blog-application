package io.mountblue.blog_application_project.controller;

import io.mountblue.blog_application_project.entity.Comment;
import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.entity.Tag;
import io.mountblue.blog_application_project.service.PostService;
import io.mountblue.blog_application_project.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
    public String showHomePage(Model model,@RequestParam(value="searchTerm",defaultValue = "" ) String searchTerm,@RequestParam(value="sortOrder",required = false) String sortOrder) {
        tagService.cleanUpTags();
        List<Post> posts=null;
        if(!searchTerm.trim().isEmpty()) {
             posts = postService.searchPosts(searchTerm);
        }
        else if(sortOrder!=null)
        {
            posts=postService.sortPosts(sortOrder);
        }
        else
        {
            posts=postService.getAllPosts();
        }
        model.addAttribute("searchTerm",searchTerm);
        model.addAttribute("posts", posts);
        return "home";
    }
    @GetMapping("/posts/{id}")
    public String showPostPage(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        List<Comment> commentList = post.getComments();
        model.addAttribute("post", post);
        model.addAttribute("commentList", commentList);
        model.addAttribute("commentForm", new Comment());
        return "post";
    }
    @GetMapping("/update/{id}")
    public String showUpdatePage(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
        Set<Tag> tags = post.getTags();
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : tags) {
            tagNames.add(tag.getName());
        }
        String tagsInput = String.join(",", tagNames);
        model.addAttribute("post", post);
        model.addAttribute("tagsInput", tagsInput);
        return "update-post";
    }

    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable("id") Long id, @ModelAttribute("post") Post post, @RequestParam("tagsInput") String tags) {
        Post oldPost = postService.getPostById(id);
        oldPost.setTitle(post.getTitle());
        oldPost.setUpdatedAt(LocalDateTime.now());
        oldPost.setAuthor(post.getAuthor());
        oldPost.setContent(post.getContent());
        oldPost.setExcerpt(post.getExcerpt());
        postService.savePost(oldPost);
        tagService.addTagsToPost(post.getId(), tags, true);
        return "redirect:/posts/" + post.getId();
    }
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deletePostById(id);
        tagService.cleanUpTags();
        return "redirect:/";
    }
}
