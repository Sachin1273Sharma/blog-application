package io.mountblue.blog_application_project.service;

import io.mountblue.blog_application_project.entity.Post;
import io.mountblue.blog_application_project.entity.Tag;
import io.mountblue.blog_application_project.repository.PostRepository;
import io.mountblue.blog_application_project.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final PostRepository postRepository;

    public TagService(TagRepository tagRepository, PostRepository postRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
    }

    public void addTagsToPost(Long postId, String tags, Boolean updatable) {
        Post post = postRepository.findById(postId).orElse(null);
        if (tags.equals("")) {
            Set<Tag> postTags = post.getTags();
            postTags.clear();
            cleanUpTags();
            postRepository.save(post);
            return;
        }
        String tagsList[] = tags.split(",");

        Set<Tag> postTags = post.getTags();
        if (updatable) {
            Set<Tag> newTags = new HashSet<>();

            for (String tagName : tagsList) {
                String trimTagName = tagName.trim();
                Tag tag;

                if (tagRepository.findByName(trimTagName) != null) {
                    tag = tagRepository.findByName(trimTagName);
                } else {
                    tag = createTag(trimTagName);
                }

                newTags.add(tag);
            }
            postTags.clear();
            postTags.addAll(newTags);
            for (Tag tag : newTags) {
                tag.getPosts().add(post);
            }
            cleanUpTags();
        } else {
            for (String tagName : tagsList) {
                String trimTagName = tagName.trim();
                Tag tag;
                if (tagRepository.findByName(trimTagName) != null) {
                    tag = tagRepository.findByName(trimTagName);
                } else {
                    tag = createTag(trimTagName);
                }
                postTags.add(tag);
                tag.getPosts().add(post);
            }
        }
        postRepository.save(post);
    }

    public Tag createTag(String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);
        tag.setCreatedAt(LocalDateTime.now());
        tag.setUpdatedAt(LocalDateTime.now());
        return tagRepository.save(tag);
    }

    public void cleanUpTags() {
        for (Tag tag : tagRepository.findAll()) {
            if (tag.getPosts().isEmpty()) {
                tagRepository.delete(tag);
            }
        }
    }
}
