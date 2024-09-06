package io.mountblue.blog_application_project.repository;

import io.mountblue.blog_application_project.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT DISTINCT p FROM Post p " +
            "LEFT JOIN p.tags t " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Post> searchPosts(@Param("searchTerm") String searchTerm);
}

