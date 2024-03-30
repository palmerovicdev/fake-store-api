package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryNameContains(Pageable pageable, String filter);

    boolean deleteByTitle(String title);
}