package com.suehay.fsastorageservice.service;

import com.suehay.fsastorageservice.model.entity.ImageData;
import com.suehay.fsastorageservice.model.request.UploadRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.repository.ImageDataRepository;
import com.suehay.fsastorageservice.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileFileStorageServiceImpl implements FileStorageService {
    private final ImageDataRepository imageDataRepository;
    private final ImageUtils imageUtils;
    private @Value("${spring.application.address}") String appAddress;
    private @Value("${spring.feature.file-storage}") String fileStorageAddres;

    @Override
    public GenericResponse<String> save(UploadRequest file) {
        var imageData = ImageData.builder()
                                 .name(file.name())
                                 .type(file.type())
                                 .data(imageUtils.compressImage(file.data()))
                                 .build();
        var data = imageDataRepository.save(imageData);
        return new GenericResponse<>("Success", "File saved successfully", "200", appAddress + fileStorageAddres + "find?name=" + data.getName());

    }

    @Override
    public GenericResponse<ImageData> findByName(String name) {
        var optionalImageData = imageDataRepository.findByName(name);
        try {
            var imageData = optionalImageData
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            imageData.setData(imageUtils.decompressImage(imageData.getData()));
            return new GenericResponse<>("Success", "Image found", "200", imageData);
        } catch (Throwable e) {
            log.error("Error finding image", e);
            return new GenericResponse<>("Error", "Error finding image", "500", null);
        }
    }

    @Override
    public GenericResponse<List<ImageData>> findAllByNameIn(List<String> names) {
        var optionalImageDataList = imageDataRepository.findAllByNameIn(names);
        try {
            var imageDataList = optionalImageDataList
                    .orElseThrow(() -> new RuntimeException("Images not found"));
            imageDataList.forEach(imageData -> imageData.setData(imageUtils.decompressImage(imageData.getData())));
            return new GenericResponse<>("Success", "Images found", "200", imageDataList);
        } catch (Throwable e) {
            log.error("Error finding images", e);
            return new GenericResponse<>("Error", "Error finding images", "500", null);
        }
    }

    @Override
    public void deleteByName(String name) {
        log.info("Deleting image with name: {}", name);

        imageDataRepository.findByName(name)
                           .ifPresent(imageDataRepository::delete);

        log.info("Image deleted successfully");
    }
}