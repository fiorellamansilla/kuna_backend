package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.models.Category;
import com.kuna_backend.services.CategoryService;
import com.kuna_backend.utils.Helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //GET all categories Endpoint//
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getCategories() {

        List<Category> body = categoryService.listCategories();
        return new ResponseEntity<List<Category>>(body, HttpStatus.OK);

    }

    //CREATE a new category - POST Endpoint//
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {

        if (Helper.notNull(categoryService.readCategory(category.getCategoryName()))) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "This category already exists"), HttpStatus.CONFLICT);
        }

        categoryService.createCategory(category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category successfully created"), HttpStatus.CREATED);
    }

    // UPDATE a specific category by ID -POST Endpoint//
    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Integer categoryId, @RequestBody Category category) {

        // Check if the category exists.
        if (Helper.notNull(categoryService.readCategory(categoryId))) {
            // If the category exists then update it.
            categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category Updated"), HttpStatus.OK);
        }

        // If the category doesn't exist then return an unsuccessful response.
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "This category doesn't exist"), HttpStatus.NOT_FOUND);
    }
}
