package com.assist.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.assist.internship.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    //Category findByCategoryId(long category_id);
    Category findByCategoryName(String categoryName);

}
