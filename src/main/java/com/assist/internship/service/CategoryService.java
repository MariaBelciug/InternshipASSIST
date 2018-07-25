package com.assist.internship.service;

import com.assist.internship.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public Category findByCategoryName(String name);
     //public Category findByCategoryId(long category_id);
     public void saveCategory(Category category);
     public List<Category> findAll();
}
