package com.assist.internship.service;

import com.assist.internship.model.Category;
import com.assist.internship.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


   @Override
    public Category findByCategoryName(String name) {
    return categoryRepository.findByCategoryName(name);
    }

   /* @Override
    public Category findByCategoryId(long category_id) {
        return categoryRepository.findByCategoryId(category_id);
    }*/


    @Override
    public void saveCategory(Category category){

        categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll()
    {
        return categoryRepository.findAll();
    }
}
