package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.Product;
import com.suehay.fsastorageservice.model.request.GenericPageRequest;
import com.suehay.fsastorageservice.model.request.ProductRequest;
import com.suehay.fsastorageservice.model.response.GenericPageResponse;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.repository.CategoryRepository;
import com.suehay.fsastorageservice.repository.ProductRepository;
import com.suehay.fsastorageservice.util.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final FileStorageService fileStorageService;
    private final ProductRepository productRepository;
    private final Logger logger;
    private final String CONTEXT = "ProductService::";
    private final CategoryRepository categoryRepository;

    @Override
    public GenericPageResponse<List<Product>> getProducts(GenericPageRequest<String> request) {
        var FUNCTION_CONTEXT = CONTEXT + "getProducts";
        var response = new GenericPageResponse<List<Product>>();

        if (request.getFilter() != null) {
            logger.info(FUNCTION_CONTEXT, "Getting products...");
            response.setData(productRepository.findAllByCategoryNameContains(
                    PageRequest.of(request.getPage(), request.getSize()),
                    request.getFilter()).stream().toList());
        } else {
            logger.info(FUNCTION_CONTEXT, "Getting products...");
            response.setData(productRepository.findAll(PageRequest.of(request.getPage(), request.getSize())).stream().toList());
        }

        if (response.getData().isEmpty()) {
            logger.info(FUNCTION_CONTEXT, "No products found.");
            response.setMessage("No products found.");
            response.setStatus("200");
        } else {
            logger.info(FUNCTION_CONTEXT, "Products found.");
            response.setMessage("Products found with category name: " + request.getFilter());
            response.setStatus("200");
            response.setPage(request.getPage());
            response.setSize(request.getSize());
        }
        return response;
    }

    @Override
    public GenericResponse<Product> getProduct(Long id, String title) {
        var FUNCTION_CONTEXT = CONTEXT + "getProduct";

        var response = new GenericResponse<Product>();
        Optional<Product> product;

        if (Objects.isNull(id) && Objects.isNull(title)) {
            logger.info(FUNCTION_CONTEXT, "No id or title provided. Error in request.");
            return new GenericResponse<>("No id or title provided.", "You need to provide a nonnull id or title to get a product.", "400", null);
        }

        if (!Objects.isNull(id)) {
            logger.info(FUNCTION_CONTEXT, "Id provided.");
            product = productRepository.findById(id);
        } else {
            logger.info(FUNCTION_CONTEXT, "Title provided.");
            product = productRepository.findByTitle(title);
        }

        if (product.isPresent()) {
            logger.info(FUNCTION_CONTEXT, "Founded product.");
            response.setData(product.get());
            response.setMessage("Product found.");
            response.setStatus("200");
        } else {
            logger.info(FUNCTION_CONTEXT, "Product not found.");
            response.setMessage("No product found with id: " + id);
            response.setStatus("404");
            response.setError("Not Found");
        }
        return response;
    }

    @Override
    public GenericResponse<Product> saveProduct(ProductRequest product) {
        var FUNCTION_CONTEXT = CONTEXT + "saveProduct";
        var response = new GenericResponse<Product>();
        var addresses = fileStorageService.getAddressByNames(product.getImages());

        var category = categoryRepository.findById(product.getCategory());
        var productEntity = product.getEntity();
        productEntity.setCategory(category.orElseThrow(() -> new RuntimeException("Category not found.")));

        logger.info(FUNCTION_CONTEXT, "Saving product...");

        product.setImages(addresses);
        response.setData(productRepository.save(productEntity));
        response.setMessage("Product saved with id: " + productEntity.getId());
        response.setStatus("201");
        return response;
    }

    @Override
    public GenericResponse<Product> updateProduct(ProductRequest product) {
        if (Objects.isNull(product.getId()))
            return new GenericResponse<>("No id provided.", "You need to provide a nonnull id to update a product.", "400", null);


        var FUNCTION_CONTEXT = CONTEXT + "updateProduct";
        var response = new GenericResponse<Product>();
        var productData = productRepository.findById(product.getId());

        var category = categoryRepository.findById(product.getCategory());
        var productEntity = product.getEntity();

        productEntity.setCategory(category.orElseThrow(() -> new RuntimeException("Category not found.")));
        logger.info(FUNCTION_CONTEXT, "Updating product...");

        if (productData.isPresent()) {
            logger.info(FUNCTION_CONTEXT, "Product saved.");
            var addresses = fileStorageService.getAddressByNames(product.getImages());
            product.setImages(addresses);
            response.setData(productRepository.save(product.getEntity()));
            response.setMessage("Product updated with id: " + product.getId());
            response.setStatus("200");
        } else {
            logger.info(FUNCTION_CONTEXT, "Product not found.");
            response.setMessage("No product found with id: " + product.getId());
            response.setStatus("404");
            response.setError("Not Found");
        }
        return response;
    }

    @Override
    public GenericResponse<String> deleteProduct(Long id, String title) {
        var FUNCTION_CONTEXT = CONTEXT + "deleteProduct";
        var response = new GenericResponse<String>();

        logger.info(FUNCTION_CONTEXT, "Deleting product...");

        if (Objects.isNull(id) && Objects.isNull(title)) {
            logger.info(FUNCTION_CONTEXT, "No id or title provided. Error in request.");
            return new GenericResponse<>("No id or title provided.", "You need to provide a nonnull id or title to delete a product.", "400", null);
        }

        if (!Objects.isNull(id)) {
            logger.info(FUNCTION_CONTEXT, "Id provided.");
            productRepository.deleteById(id);
        } else {
            logger.info(FUNCTION_CONTEXT, "Title provided.");
            productRepository.deleteByTitle(title);
        }

        logger.info(FUNCTION_CONTEXT, "Product deleted.");

        response.setMessage("Product deleted with id: " + id);
        response.setData(String.valueOf(id));
        response.setStatus("200");
        return response;
    }
}