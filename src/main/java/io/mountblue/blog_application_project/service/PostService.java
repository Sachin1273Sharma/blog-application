package io.mountblue.blog_application_project.service;

import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    TagService tagService;

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Set<String> getAllAuthors() {
        return postRepository.findAllAuthors();
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public Page<Post> searchPosts(String searchTerm, Pageable pageable) {
        return postRepository.searchPosts(searchTerm, pageable);
    }

    public Page<Post> filterPosts(List<String> authors, List<Long> tags, String publishedDate, Pageable pageable) {
        Page<Post> filtered;
        LocalDate parsedDate = null;

        if (publishedDate != null && !publishedDate.isEmpty()) {
            parsedDate = LocalDate.parse(publishedDate);
        }

        if (authors != null && !authors.isEmpty() && tags != null && !tags.isEmpty() && parsedDate != null) {
            filtered = postRepository.findByAuthorInAndTagsIdInAndCreatedAt(authors, tags, parsedDate, pageable);
        } else if ((authors == null || authors.isEmpty()) && (tags != null && !tags.isEmpty()) && parsedDate != null) {
            filtered = postRepository.findByTagsIdInAndCreatedAt(tags, parsedDate, pageable);
        } else if ((authors != null && !authors.isEmpty()) && (tags == null || tags.isEmpty()) && parsedDate != null) {
            filtered = postRepository.findByAuthorInAndCreatedAt(authors, parsedDate, pageable);
        } else if (authors != null && !authors.isEmpty() && tags != null && !tags.isEmpty()) {
            filtered = postRepository.findByAuthorInAndTagsIdIn(authors, tags, pageable);
        } else if ((authors == null || authors.isEmpty()) && (tags != null && !tags.isEmpty())) {
            filtered = postRepository.findByTagsIdIn(tags, pageable);
        } else if ((authors != null && !authors.isEmpty()) && (tags == null || tags.isEmpty())) {
            filtered = postRepository.findByAuthorIn(authors, pageable);
        } else if (parsedDate != null) {
            filtered = postRepository.findByCreatedAt(parsedDate, pageable);
        } else {
            filtered = postRepository.findAll(pageable);
        }
        return filtered;
    }
    public void updatePost(Long id, Post updatedPost, String tags)
    {
        Post oldPost = getPostById(id);
        oldPost.setTitle(updatedPost.getTitle());
        oldPost.setUpdatedAt(LocalDateTime.now());
        oldPost.setAuthor(updatedPost.getAuthor());
        oldPost.setContent(updatedPost.getContent());
        oldPost.setExcerpt(updatedPost.getExcerpt());
        savePost(oldPost);
        tagService.addTagsToPost(id, tags, true);
    }

    public void createNewPost(Post post, String tags) {
        post.setCreatedAt(LocalDate.now());
        post.setIsPublished(true);
        post.setPublishedAt(LocalDateTime.now());
        savePost(post);
        tagService.addTagsToPost(post.getId(), tags, false);
    }
}