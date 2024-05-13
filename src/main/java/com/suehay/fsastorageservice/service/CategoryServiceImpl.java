package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.Category;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.repository.CategoryRepository;
import com.suehay.fsastorageservice.util.Logger;
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
    private final Logger logger;
    private final String CONTEXT = "CategoryService::";

    @Override
    public GenericResponse<Category> saveCategory(Category category) {
        var FUNCTION_CONTEXT = "saveCategory";
        var response = new GenericResponse<Category>();

        logger.info(FUNCTION_CONTEXT, "Saving category...");

        var addresses = fileStorageService.getAddressByNames(List.of(category.getImage()));

        category.setImage(!addresses.isEmpty() ? addresses.get(0) : null);
        response.setData(categoryRepository.save(category));
        response.setMessage("Category saved with id: " + category.getId());
        response.setStatus("200");

        logger.info(FUNCTION_CONTEXT, "Category saved.");

        return response;
    }

    @Override
    public GenericResponse<Category> getCategory(String id) {
        var FUNCTION_CONTEXT = CONTEXT + "getCategory";
        var response = new GenericResponse<Category>();
        var category = categoryRepository.findById(Long.parseLong(id));

        if (category.isPresent()) {
            logger.info(FUNCTION_CONTEXT, "Category found with id: " + id);
            response.setData(category.get());
            response.setMessage("Category found with id: " + id);
            response.setStatus("200");
        } else {
            logger.info(FUNCTION_CONTEXT, "No category found with id: " + id);
            response.setMessage("No category found with id: " + id);
            response.setStatus("404");
            response.setError("Not Found");
        }
        return response;
    }

    @Override
    public GenericResponse<Page<Category>> getCategories(GenericPageRequest<String> request) {
        var FUNCTION_CONTEXT = CONTEXT + "getCategories";
        var response = new GenericResponse<Page<Category>>();

        if (request.getFilter() != null) {
            logger.info(FUNCTION_CONTEXT, "Getting categories with name: " + request.getFilter());
            response.setData(categoryRepository.findAllByNameContains(PageRequest.of(request.getPage(), request.getSize()), request.getFilter()));
        } else {
            logger.info(FUNCTION_CONTEXT, "Getting all categories...");
            response.setData(categoryRepository.findAll(PageRequest.of(request.getPage(), request.getSize())));
        }

        if (response.getData().isEmpty()) {
            logger.info(FUNCTION_CONTEXT, "No categories found with name: " + request.getFilter());
            response.setMessage("No categories found with name: " + request.getFilter());
            response.setStatus("404");
            response.setError("Not Found");
        } else {
            logger.info(FUNCTION_CONTEXT, "Categories found with name: " + request.getFilter());
            response.setMessage("Categories found with name: " + request.getFilter());
            response.setStatus("200");
        }
        return response;
    }

    @Override
    public GenericResponse<Category> deleteCategory(String id, String name) {
        var FUNCTION_CONTEXT = CONTEXT + "deleteCategory";
        var response = new GenericResponse<Category>();

        categoryRepository.deleteById(Long.parseLong(id));
        response.setMessage("Category deleted with id: " + id);
        response.setStatus("200");
        logger.info(FUNCTION_CONTEXT, "Category deleted with id: " + id);
        return response;
    }

    @Override
    public GenericResponse<Category> updateCategory(Category category) {
        var FUNCTION_CONTEXT = CONTEXT + "updateCategory";
        var response = new GenericResponse<Category>();

        var addresses = fileStorageService.getAddressByNames(List.of(category.getImage()));
        category.setImage(!addresses.isEmpty() ? addresses.get(0) : null);

        logger.info(FUNCTION_CONTEXT, "Updating category with id: " + category.getId());
        response.setData(categoryRepository.save(category));
        response.setMessage("Category updated with id: " + category.getId());
        response.setStatus("200");
        logger.info(FUNCTION_CONTEXT, "Category updated with id: " + category.getId());
        return response;
    }
}