package com.kuna_backend;

import com.kuna_backend.models.Category;
import com.kuna_backend.repositories.CategoryRepository;
import com.kuna_backend.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void listCategories_WhenCategoriesExist_ReturnsListOfCategories() {

        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.listCategories();

        assertEquals(categories, result);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void createCategory_WhenCalled_SavesCategory() {

        Category category = new Category();

        categoryService.createCategory(category);

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void readCategoryByName_WhenCategoryExists_ReturnsCategory() {

        String categoryName = "testCategoryName";
        Category category = new Category();
        when(categoryRepository.findByCategoryName(categoryName)).thenReturn(category);

        Category result = categoryService.readCategory(categoryName);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).findByCategoryName(categoryName);
    }

    @Test
    public void readCategoryById_WhenCategoryExists_ReturnsCategory() {

        Long categoryId = 1L;
        Optional<Category> category = Optional.of(new Category());
        when(categoryRepository.findById(categoryId)).thenReturn(category);

        Optional<Category> result = categoryService.readCategory(categoryId);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    public void updateCategory_WhenCategoryExists_UpdatesCategory() {

        Long categoryId = 1L;
        Category existingCategory = new Category();
        Category newCategory = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));

        categoryService.updateCategory(categoryId, newCategory);

        assertEquals(newCategory.getCategoryName(), existingCategory.getCategoryName());
        assertEquals(newCategory.getDescription(), existingCategory.getDescription());
        assertEquals(newCategory.getProducts(), existingCategory.getProducts());
        assertEquals(newCategory.getImageUrl(), existingCategory.getImageUrl());
        verify(categoryRepository, times(1)).save(existingCategory);
    }
}
