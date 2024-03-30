package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.Product;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;

import java.util.List;

public interface ProductService {
    GenericResponse<List<Product>> getProducts(GenericPageRequest<String> request);

    GenericResponse<Product> getProductById(String id);

    GenericResponse<Product> saveProduct(Product product);

    GenericResponse<Product> updateProduct(Product product);

    GenericResponse<String> deleteProductByName(String title);

    GenericResponse<Product> getProductByName(String name);
}