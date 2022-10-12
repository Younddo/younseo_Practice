package com.sparta.younseo.repository;

import com.sparta.younseo.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByOrderByModifiedAtDesc();
    List<Blog> findAllByOrderByCreatedAtDesc();
}
