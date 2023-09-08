package com.example.newmockup.repository;

import com.example.newmockup.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findTodoByCategoryId(Long categoryId);
}
