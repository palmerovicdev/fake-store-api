package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.Product;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.request.ProductRequest;
import com.suehay.fsastorageservice.model.response.GenericPageResponse;
import com.suehay.fsastorageservice.model.response.GenericResponse;

import java.util.List;

public interface ProductService {
    GenericPageResponse<List<Product>> getProducts(GenericPageRequest<String> request);

    GenericResponse<Product> getProduct(Long id, String title);

    GenericResponse<Product> saveProduct(ProductRequest product);

    GenericResponse<Product> updateProduct(ProductRequest product);

    GenericResponse<String> deleteProduct(Long id, String title);
}