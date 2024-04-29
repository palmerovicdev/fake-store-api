package com.suehay.fsastorageservice.controller;

import com.suehay.fsastorageservice.model.entity.Product;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get all products", description = "Get all products", responses = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "404", description = "No products found"),
            @ApiResponse(responseCode = "500", description = "Error finding products")
    }, method = "GET")
    @GetMapping("/get")
    public ResponseEntity<GenericResponse<List<Product>>> getProducts(@RequestBody GenericPageRequest<String> filter) {
        return ResponseEntity.ok(productService.getProducts(filter));
    }

    @Operation(summary = "Get product by id", description = "Get product by id", responses = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "No product found"),
            @ApiResponse(responseCode = "500", description = "Error finding product")
    }, method = "GET")
    @GetMapping("/get/{id}")
    public ResponseEntity<GenericResponse<Product>> getProductById(@PathVariable @NotNull String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Get product by name", description = "Get product by name", responses = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "No product found"),
            @ApiResponse(responseCode = "500", description = "Error finding product")
    }, method = "GET")
    @GetMapping("/get/{name}")
    public ResponseEntity<GenericResponse<Product>> getProductByName(@PathVariable @NotNull String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
    }

    @Operation(summary = "Save product", description = "Save product", responses = {
            @ApiResponse(responseCode = "200", description = "Product saved"),
            @ApiResponse(responseCode = "500", description = "Error saving product")
    }, method = "POST")
    @PostMapping("/save")
    public ResponseEntity<GenericResponse<Product>> saveProduct(@RequestBody @NotNull Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
    }

    @Operation(summary = "Update product", description = "Update product", responses = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "500", description = "Error updating product")
    }, method = "PUT")
    @PutMapping("/update")
    public ResponseEntity<GenericResponse<Product>> updateProduct(@RequestBody @NotNull Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @Operation(summary = "Delete product", description = "Delete product", responses = {
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "500", description = "Error deleting product")
    }, method = "DELETE")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse<String>> deleteProduct(@PathVariable @NotNull String id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @Operation(summary = "Delete product by name", description = "Delete product by name", responses = {
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "500", description = "Error deleting product")
    }, method = "DELETE")
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<GenericResponse<String>> deleteProductByName(@PathVariable @NotNull String name) {
        return ResponseEntity.ok(productService.deleteProductByName(name));
    }
}