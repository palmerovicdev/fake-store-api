package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByNameContains(Pageable pageable, String filter);

    Optional<Category> findByName(String name);

    void deleteById(Long id);

    boolean deleteByName(String name);
}