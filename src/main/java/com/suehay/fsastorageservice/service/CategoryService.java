package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.Category;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    GenericResponse<Category> saveCategory(Category category);

    GenericResponse<Category> getCategoryById(String id);

    GenericResponse<Category> getCategoryByName(String name);

    GenericResponse<Page<Category>> getCategories(GenericPageRequest<String> request);

    GenericResponse<Category> deleteCategoryByName(String name);

    GenericResponse<Category> deleteCategory(String id);

    GenericResponse<Category> updateCategory(Category category);
}