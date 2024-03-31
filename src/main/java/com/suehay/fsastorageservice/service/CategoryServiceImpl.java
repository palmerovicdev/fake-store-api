package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.Category;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    @Override
    public GenericResponse<Category> saveCategory(Category category) {
        var response = new GenericResponse<Category>();
        var addresses = fileStorageService.getAddressByNames(List.of(category.getImage()));
        category.setImage(!addresses.isEmpty() ? addresses.get(0) : null);
        response.setData(categoryRepository.save(category));
        response.setMessage("Category saved with id: " + category.getId());
        response.setStatus("200");
        return response;
    }

    @Override
    public GenericResponse<Category> getCategoryById(String id) {
        var response = new GenericResponse<Category>();
        var category = categoryRepository.findById(Long.parseLong(id));
        if (category.isPresent()) {
            response.setData(category.get());
            response.setMessage("Category found with id: " + id);
            response.setStatus("200");
        } else {
            response.setMessage("No category found with id: " + id);
            response.setStatus("404");
            response.setError("Not Found");
        }
        return response;
    }

    @Override
    public GenericResponse<Category> getCategoryByName(String name) {
        var response = new GenericResponse<Category>();
        var category = categoryRepository.findByName(name);
        if (category.isPresent()) {
            response.setData(category.get());
            response.setMessage("Category found with name: " + name);
            response.setStatus("200");
        } else {
            response.setMessage("No category found with name: " + name);
            response.setStatus("404");
            response.setError("Not Found");
        }
        return response;
    }

    @Override
    public GenericResponse<Page<Category>> getCategories(GenericPageRequest<String> request) {
        var response = new GenericResponse<Page<Category>>();
        if (request.getFilter() != null) {
            response.setData(categoryRepository.findAllByNameContains(PageRequest.of(request.getPage(), request.getSize()), request.getFilter()));
        } else {
            response.setData(categoryRepository.findAll(PageRequest.of(request.getPage(), request.getSize())));
        }
        if (response.getData().isEmpty()) {
            response.setMessage("No categories found with name: " + request.getFilter());
            response.setStatus("404");
            response.setError("Not Found");
        } else {
            response.setMessage("Categories found with name: " + request.getFilter());
            response.setStatus("200");
        }
        return response;
    }

    @Override
    public GenericResponse<Category> deleteCategoryByName(String name) {
        var response = new GenericResponse<Category>();
        var repositoryResponse = categoryRepository.deleteByName(name);
        if (repositoryResponse) {
            response.setMessage("Category deleted with name: " + name);
            response.setStatus("200");
        } else {
            response.setMessage("No repository response found with name: " + name);
            response.setStatus("404");
            response.setError("Not Found");
        }
        return response;
    }

    @Override
    public GenericResponse<Category> deleteCategory(String id) {
        var response = new GenericResponse<Category>();
        categoryRepository.deleteById(Long.parseLong(id));
        response.setMessage("Category deleted with id: " + id);
        response.setStatus("200");
        return response;
    }

    @Override
    public GenericResponse<Category> updateCategory(Category category) {
        var response = new GenericResponse<Category>();
        var addresses = fileStorageService.getAddressByNames(List.of(category.getImage()));
        category.setImage(!addresses.isEmpty() ? addresses.get(0) : null);
        response.setData(categoryRepository.save(category));
        response.setMessage("Category updated with id: " + category.getId());
        response.setStatus("200");
        return response;
    }
}