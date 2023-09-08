package com.example.newmockup.repository;

import com.example.newmockup.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findCategoryByUserId(Long userId);

}
