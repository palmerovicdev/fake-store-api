package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.Product;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    GenericResponse<Page<Product>> getProducts(GenericPageRequest<String> request);

    GenericResponse<Product> getProductById(String id);

    GenericResponse<Product> saveProduct(Product product);

    GenericResponse<Product> updateProduct(Product product);

    GenericResponse<String> deleteProductByName(String title);
}