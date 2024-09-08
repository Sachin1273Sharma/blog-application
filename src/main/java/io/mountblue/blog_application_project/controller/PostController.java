package io.mountblue.blog_application_project.controller;

import io.mountblue.blog_application_project.entity.Comment;
import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.entity.Tag;
import io.mountblue.blog_application_project.repository.TagRepository;
import io.mountblue.blog_application_project.service.PostService;
import io.mountblue.blog_application_project.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
    @Autowired
    TagRepository tagrepository;

    @GetMapping("/posts/new")
    public String showCreatePostPage(Model model) {
        model.addAttribute("post", new Post());
        return "create-post";
    }

    @PostMapping("/posts/save")
    public String savePost(@ModelAttribute("post") Post post, @RequestParam("tagsInput") String tags) {
        postService.createNewPost(post, tags);
        return "redirect:/";
    }

    @GetMapping("/")
    public String showHomePage(Model model, @RequestParam(required = false) String searchTerm, @RequestParam(value = "sortOrder", defaultValue = "") String sortOrder, @RequestParam(value = "authorsList", required = false) List<String> authorsList,
                               @RequestParam(value = "tagsList", required = false) List<Long> tagsList
            , @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "4") Integer size, @RequestParam(value = "publishedDate", required = false) String publishedDate) {
        tagService.cleanUpTags();
        Sort sort;
        if (sortOrder.equals("asc")) {
            sort = Sort.by(Sort.Direction.ASC, "publishedAt");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "publishedAt");
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        int totalPages = 0;
        List<Post> posts;
        if (authorsList != null && !authorsList.isEmpty() || tagsList != null && !tagsList.isEmpty() || publishedDate != null && !publishedDate.trim().isEmpty()) {
            Page<Post> filterPosts = postService.filterPosts(authorsList, tagsList, publishedDate, pageable);
            totalPages = filterPosts.getTotalPages();
            posts = filterPosts.getContent();
        } else if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            Page<Post> searchedPosts = postService.searchPosts(searchTerm, pageable);
            posts = searchedPosts.getContent();
            totalPages = searchedPosts.getTotalPages();
        } else {
            Page<Post> allPosts = postService.getAllPosts(pageable);
            posts = allPosts.getContent();
            totalPages = allPosts.getTotalPages();
        }

        model.addAttribute("posts", posts);
        model.addAttribute("authors", postService.getAllAuthors());
        model.addAttribute("tags", tagrepository.findAll());
        model.addAttribute("pageNumber", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("sortOrder", sortOrder);
        model.addAttribute("authorsList", authorsList != null ? authorsList : Collections.emptyList());
        model.addAttribute("tagsList", tagsList != null ? tagsList : Collections.emptyList());
        model.addAttribute("publishedDate", publishedDate);
        return "home";
    }

    @GetMapping("/posts/{id}")
    public String showPostPage(@PathVariable("id") Long id, Model model, @RequestParam(value = "sortOrder", required = false) String sortOrder, @RequestParam(value = "searchTerm", defaultValue = "") String searchTerm,
                               @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "authorsList", required = false) List<String> authorsList, @RequestParam(value = "tagsList", required = false) List<Long> tagsList) {
        Post post = postService.getPostById(id);
        List<Comment> commentList = post.getComments();

        model.addAttribute("post", post);
        model.addAttribute("commentList", commentList);
        model.addAttribute("commentForm", new Comment());
        model.addAttribute("sortOrder", sortOrder != null ? sortOrder : "");
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("page", page);
        model.addAttribute("authorsList", (authorsList != null && !authorsList.isEmpty()) ? authorsList : Collections.emptyList());
        model.addAttribute("tagsList", (tagsList != null && !tagsList.isEmpty()) ? tagsList : Collections.emptyList());
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
        postService.updatePost(id, post, tags);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id) {
        postService.deletePostById(id);
        tagService.cleanUpTags();
        return "redirect:/";
    }
}
