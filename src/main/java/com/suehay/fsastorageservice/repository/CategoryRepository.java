package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}