package io.mountblue.blog_application_project.repository;

import io.mountblue.blog_application_project.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT  p.author FROM Post p")
    Set<String> findAllAuthors();

    @Query("SELECT DISTINCT p FROM Post p " +
            "LEFT JOIN p.tags t " +
            "WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Post> searchPosts(@Param("searchTerm") String searchTerm, Pageable pageable);

    Page<Post> findByAuthorInAndTagsIdInAndCreatedAt(List<String> authors, List<Long> tags, LocalDate date, Pageable pageable);

    Page<Post> findByTagsIdIn(List<Long> tags, Pageable pageable);

    Page<Post> findByAuthorIn(List<String> authors, Pageable pageable);

    Page<Post> findByTagsIdInAndCreatedAt(List<Long> tags, LocalDate createdAt, Pageable pageable);

    Page<Post> findByAuthorInAndCreatedAt(List<String> authors, LocalDate createdAt, Pageable pageable);

    Page<Post> findByAuthorInAndTagsIdIn(List<String> authors, List<Long> tags, Pageable pageable);

    Page<Post> findByCreatedAt(LocalDate createdAt, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Post p WHERE p.id = :id")
    void deleteByIdCustom(@Param("id") Long id);
}
