package com.assist.internship.controller;

import com.assist.internship.helpers.InternshipResponse;
import com.assist.internship.model.Category;
import com.assist.internship.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
/*

    @RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }
*/

    //GET   /categories - get all the course categories:OK

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity getCategories(@RequestHeader("reset_token") final String token)
    {
        if(token.isEmpty() || token == null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, categoryService.findAll().toString(), null));
        }
    }

        //  POST  /create/category - create a new course category - data sent in the request body
        @RequestMapping(value="create/category", method=RequestMethod.POST)
        public ResponseEntity createCategory(@RequestBody Category category)

        {
           Category dbCategory= categoryService.findByCategoryName(category.getName());


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
