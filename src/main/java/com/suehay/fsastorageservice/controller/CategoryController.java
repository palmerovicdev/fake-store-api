package com.suehay.fsastorageservice.controller;

import com.suehay.fsastorageservice.model.entity.Category;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Save a category", description = "Save a category", responses = {
            @ApiResponse(responseCode = "201", description = "Category saved"),
            @ApiResponse(responseCode = "500", description = "Error finding categories")
    }, method = "POST")
    @PostMapping("/save")
    public ResponseEntity<GenericResponse<Category>> saveCategory(@RequestBody @NotNull Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(category));
    }

    @Operation(summary = "Get category by id", description = "Get category by id", responses = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "No category found"),
            @ApiResponse(responseCode = "500", description = "Error finding category")
    }, method = "GET")
    @GetMapping("/get/{id}")
    public ResponseEntity<GenericResponse<Category>> getCategoryById(@PathVariable @NotNull String id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Get all categories", description = "Get all categories", responses = {
            @ApiResponse(responseCode = "200", description = "Categories found"),
            @ApiResponse(responseCode = "404", description = "No categories found"),
            @ApiResponse(responseCode = "500", description = "Error finding categories")
    }, method = "GET")
    @GetMapping("/findAll")
    public ResponseEntity<GenericResponse<?>> getCategories(@RequestBody GenericPageRequest<String> filter) {
        return ResponseEntity.ok(categoryService.getCategories(filter));
    }

    @Operation(summary = "Update category", description = "Update category", responses = {
            @ApiResponse(responseCode = "200", description = "Category updated"),
            @ApiResponse(responseCode = "404", description = "No category found"),
            @ApiResponse(responseCode = "500", description = "Error updating category")
    }, method = "PUT")
    @PutMapping("/update")
    public ResponseEntity<GenericResponse<Category>> updateCategory(@RequestBody @NotNull Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(category));
    }

    @Operation(summary = "Delete category", description = "Delete category", responses = {
            @ApiResponse(responseCode = "200", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "No category found"),
            @ApiResponse(responseCode = "500", description = "Error deleting category")
    }, method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse<Category>> deleteCategory(@PathVariable @NotNull String id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    @Operation(summary = "Delete category by name", description = "Delete category by name", responses = {
            @ApiResponse(responseCode = "200", description = "Category deleted"),
            @ApiResponse(responseCode = "404", description = "No category found"),
            @ApiResponse(responseCode = "500", description = "Error deleting category")
    }, method = "DELETE")
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<GenericResponse<Category>> deleteCategoryByName(@PathVariable @NotNull String name) {
        return ResponseEntity.ok(categoryService.deleteCategoryByName(name));
    }

}