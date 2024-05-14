package com.suehay.fsastorageservice.controller;

import com.suehay.fsastorageservice.model.entity.Product;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.request.ProductRequest;
import com.suehay.fsastorageservice.model.response.GenericPageResponse;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Tag(name = "Product", description = "Product controller")
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
    public ResponseEntity<GenericPageResponse<List<Product>>> getProducts(
            @Parameter(description = "The number of the page.")
            @RequestParam(required = false) Integer page,
            @Parameter(description = "The size of the page.")
            @RequestParam(required = false) Integer size,
            @Parameter(description = "The name of the category.")
            @RequestParam(required = false) String filter
                                                                         ) {
        return ResponseEntity.ok(productService.getProducts(new GenericPageRequest<>(
                !Objects.isNull(page) ? page : 0,
                !Objects.isNull(size) ? size : 10,
                filter)));
    }

    @Operation(summary = "Get product by id or name.", description = "Get product by id or name.", responses = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "No product found"),
            @ApiResponse(responseCode = "500", description = "Error finding product")
    }, method = "GET")
    @GetMapping("/getAll")
    public ResponseEntity<GenericResponse<Product>> getProductById(
            @Parameter(description = "Product id")
            @RequestParam(required = false) Long id,
            @Parameter(description = "Product title")
            @RequestParam(required = false) String title) {
        if (Objects.isNull(id) && Objects.isNull(title)) return ResponseEntity.badRequest().body(
                new GenericResponse<>(
                        "No id or title provided.",
                        "You need to provide a nonnull id or title to get a single product.",
                        HttpStatus.BAD_REQUEST.name(),
                        null
                ));
        return ResponseEntity.ok(productService.getProduct(id, title));
    }

    @Operation(summary = "Save product", description = "Save product", responses = {
            @ApiResponse(responseCode = "200", description = "Product saved"),
            @ApiResponse(responseCode = "500", description = "Error saving product")
    }, method = "POST")
    @PostMapping("/save")
    public ResponseEntity<GenericResponse<Product>> saveProduct(@RequestBody @NotNull ProductRequest product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(product));
    }

    @Operation(summary = "Update product", description = "Update product", responses = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "500", description = "Error updating product")
    }, method = "PUT")
    @PutMapping("/update")
    public ResponseEntity<GenericResponse<Product>> updateProduct(@RequestBody @NotNull ProductRequest product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @Operation(summary = "Delete product by id or title", description = "Delete product by id or name", responses = {
            @ApiResponse(responseCode = "200", description = "Product deleted"),
            @ApiResponse(responseCode = "500", description = "Error deleting product")
    }, method = "DELETE")
    @DeleteMapping("/delete")
    public ResponseEntity<GenericResponse<String>> deleteProduct(
            @Parameter(description = "Product id")
            @RequestParam(required = false) Long id,
            @Parameter(description = "Product title")
            @RequestParam(required = false) String title
                                                                ) {
        if (Objects.isNull(id) && Objects.isNull(title)) return ResponseEntity.badRequest().body(
                new GenericResponse<>(
                        "No id or title provided.",
                        "You need to provide a nonnull id or title to delete a product.",
                        HttpStatus.BAD_REQUEST.name(),
                        null
                ));
        return ResponseEntity.ok(productService.deleteProduct(id, title));
    }
}