package io.mountblue.blog_application_project.service;

import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
    public List<Post> getAllPosts() {
       return postRepository.findAll();
    }
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> searchPosts(String searchTerm) {
        return postRepository.searchPosts(searchTerm);
    }

    public List<Post> sortPosts(String sortOrder) {
        List<Post> posts=null;
        if(sortOrder.equals("asc"))
        {
            posts=postRepository.findAll(Sort.by(Sort.Direction.ASC,"publishedAt"));

        }
        else
        {
            posts=postRepository.findAll(Sort.by(Sort.Direction.DESC,"publishedAt"));
        }
        return posts;
    }
}
