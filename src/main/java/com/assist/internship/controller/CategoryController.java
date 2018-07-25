package com.assist.internship.controller;

import com.assist.internship.helpers.InternshipResponse;
import com.assist.internship.model.Category;
import com.assist.internship.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //GET   /categories - get all the course categories:OK
    @RequestMapping(value="/categories", method=RequestMethod.GET)
    public List<Category> getCategories (){

        return categoryService.findAll();

    }

        //  POST  /create/category - create a new course category - data sent in the request body
        @RequestMapping(value="create/category", method=RequestMethod.POST)
        public ResponseEntity createCategory(@RequestBody Category category)

        {
           Category dbCategory= categoryService.findByCategoryName(category.getCategoryName());


          if (dbCategory == null) {

              categoryService.saveCategory(category);
              return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(category)));

              }
              else
                 {
                     return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Category  exist please create another category", null));

           }
        }


}
