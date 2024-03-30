package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.ImageData;
import com.suehay.fsastorageservice.model.request.UploadRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService  {
    GenericResponse<String> save(UploadRequest file);
    GenericResponse<ImageData> findByName(String name);
    GenericResponse<List<ImageData>> findAllByNameIn(List<String> names);
    void deleteByName(String name);
}