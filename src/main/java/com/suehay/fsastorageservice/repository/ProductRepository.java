package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByCategoryNameContains(Pageable pageable, String filter);

    boolean deleteByTitle(String title);

    Optional<Product> findByTitle(String name);
    void deleteById(Long id);
}