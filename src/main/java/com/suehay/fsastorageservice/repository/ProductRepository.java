package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.Product;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName ORDER BY p.id ASC")
    Page<Product> findAllByCategoryNameContains(Pageable pageable, String categoryName);

    boolean deleteByTitle(String name);
}