package com.example.newmockup.service;


import com.example.newmockup.dto.CategoryDto;
import com.example.newmockup.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto save(CategoryDto category) {
        return null;
    }

    @Override
    public List<CategoryDto> findAll() {
        return null;
    }

    @Override
    public CategoryDto findById(Long id) {
        return null;
    }

    @Override
    public List<CategoryDto> findAllByUserId(Long userId) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<CategoryDto> getAllTodoByCategoriesForToday(Long userId) {
        return null;
    }
}
