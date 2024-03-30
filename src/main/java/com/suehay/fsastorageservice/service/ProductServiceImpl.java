package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.Product;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public GenericResponse<Page<Product>> getProducts(GenericPageRequest<String> request) {
        var response = new GenericResponse<Page<Product>>();
        if (request.getFilter() != null) {
            response.setData(productRepository.findAllByCategoryNameContains(PageRequest.of(request.getPage(), request.getSize()), request.getFilter()));
        } else {
            response.setData(productRepository.findAll(PageRequest.of(request.getPage(), request.getSize())));
        }
        if (response.getData().isEmpty()) {
            response.setMessage("No products found with category name: " + request.getFilter());
            response.setStatus("404");
            response.setError("Not Found");
        } else {
            response.setMessage("Products found with category name: " + request.getFilter());
            response.setStatus("200");
        }
        return response;
    }

    @Override
    public GenericResponse<Product> getProductById(String id) {
        var response = new GenericResponse<Product>();
        var product = productRepository.findById(Long.parseLong(id));
        if (product.isPresent()) {
            response.setData(product.get());
            response.setMessage("Product found with id: " + id);
            response.setStatus("200");
        } else {
            response.setMessage("No product found with id: " + id);
            response.setStatus("404");
            response.setError("Not Found");
        }
        return response;
    }

    @Override
    public GenericResponse<Product> saveProduct(Product product) {
        var response = new GenericResponse<Product>();
        response.setData(productRepository.save(product));
        response.setMessage("Product saved with id: " + product.getId());
        response.setStatus("201");
        return response;
    }

    @Override
    public GenericResponse<Product> updateProduct(Product product) {
        var response = new GenericResponse<Product>();
        var productData = productRepository.findById(product.getId());
        if (productData.isPresent()) {
            response.setData(productRepository.save(product));
            response.setMessage("Product updated with id: " + product.getId());
            response.setStatus("200");
        } else {
            response.setMessage("No product found with id: " + product.getId());
            response.setStatus("404");
            response.setError("Not Found");
        }
        return response;
    }

    @Override
    public GenericResponse<String> deleteProductByName(String title) {
        var response = new GenericResponse<String>();
        var repositoryResponse = productRepository.deleteByTitle(title);
        if (repositoryResponse) {
            response.setMessage("Product deleted with title: " + title);
            response.setData(title);
            response.setStatus("200");
        } else {
            response.setMessage("No product found with title: " + title);
            response.setStatus("404");
            response.setError("Not Found");
        }
        return response;
    }
}