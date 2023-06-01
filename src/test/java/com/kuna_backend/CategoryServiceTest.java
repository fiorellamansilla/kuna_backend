package com.kuna_backend;

import com.kuna_backend.models.Category;
import com.kuna_backend.repositories.CategoryRepository;
import com.kuna_backend.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListCategories() {

        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.listCategories();

        assertEquals(categories, result);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testCreateCategory() {

        Category category = new Category();

        categoryService.createCategory(category);

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testReadCategoryByCategoryName() {

        String categoryName = "exampleCategoryName";
        Category category = new Category();
        when(categoryRepository.findByCategoryName(categoryName)).thenReturn(category);

        Category result = categoryService.readCategory(categoryName);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).findByCategoryName(categoryName);
    }

    @Test
    public void testReadCategoryByCategoryId() {

        Integer categoryId = 1;
        Optional<Category> category = Optional.of(new Category());
        when(categoryRepository.findById(categoryId)).thenReturn(category);

        Optional<Category> result = categoryService.readCategory(categoryId);

        assertEquals(category, result);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    public void testUpdateCategory() {

        Integer categoryId = 1;
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
