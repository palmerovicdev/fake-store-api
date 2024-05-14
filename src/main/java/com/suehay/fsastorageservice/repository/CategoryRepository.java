package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.Category;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByNameContains(Pageable pageable, String filter);

    void deleteById(@NonNull Long id);

}