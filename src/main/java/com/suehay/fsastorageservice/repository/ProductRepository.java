package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}