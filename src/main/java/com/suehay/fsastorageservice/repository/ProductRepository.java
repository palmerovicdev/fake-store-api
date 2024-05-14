package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.Product;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.title LIKE %:filter%")
    Page<Product> findAllByCategoryNameContains(Pageable pageable, String filter);

    void deleteByTitle(String title);

    Optional<Product> findByTitle(String name);

    void deleteById(@NonNull Long id);
}