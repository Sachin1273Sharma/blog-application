package io.mountblue.blog_application_project.repository;

import io.mountblue.blog_application_project.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag  findByName(String name);
}