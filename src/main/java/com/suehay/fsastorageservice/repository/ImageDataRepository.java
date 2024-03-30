package com.suehay.fsastorageservice.repository;

import com.suehay.fsastorageservice.model.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByName(String name);

    Optional<List<ImageData>> findAllByNameIn(List<String> names);
}