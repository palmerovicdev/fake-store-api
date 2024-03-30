package com.suehay.fsastorageservice.controller;

import com.suehay.fsastorageservice.model.entity.Category;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "Get all categories", responses = {
            @ApiResponse(responseCode = "200", description = "Categories found"),
            @ApiResponse(responseCode = "404", description = "No categories found"),
            @ApiResponse(responseCode = "500", description = "Error finding categories")
    })
    @PostMapping("/save")
    public ResponseEntity<GenericResponse<Category>> saveCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @Operation(summary = "Get category by id", description = "Get category by id", responses = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "No category found"),
            @ApiResponse(responseCode = "500", description = "Error finding category")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<GenericResponse<Category>> getCategoryById(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Get all categories", description = "Get all categories", responses = {
            @ApiResponse(responseCode = "200", description = "Categories found"),
            @ApiResponse(responseCode = "404", description = "No categories found"),
            @ApiResponse(responseCode = "500", description = "Error finding categories")
    })
    @PostMapping("/findAll")
    public ResponseEntity<GenericResponse<?>> getCategories(@RequestBody GenericPageRequest<String> filter) {
        return ResponseEntity.ok(categoryService.getCategories(filter));
    }
}