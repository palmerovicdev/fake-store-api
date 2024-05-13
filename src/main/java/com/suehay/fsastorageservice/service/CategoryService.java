package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.Category;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import org.springframework.data.domain.Page;

public interface CategoryService {
    GenericResponse<Category> saveCategory(Category category);

    GenericResponse<Category> getCategory(String id);

    GenericResponse<Page<Category>> getCategories(GenericPageRequest<String> request);

    GenericResponse<Category> deleteCategory(String id, String name);

    GenericResponse<Category> updateCategory(Category category);
}