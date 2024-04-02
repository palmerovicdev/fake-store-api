package com.suehay.fsastorageservice.controller;

import com.suehay.fsastorageservice.model.entity.ImageData;
import com.suehay.fsastorageservice.model.request.UploadRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @Operation(summary = "Save file", description = "Save file to database", responses = {
            @ApiResponse(responseCode = "200", description = "File saved successfully"),
            @ApiResponse(responseCode = "400", description = "Error saving file"),
            @ApiResponse(responseCode = "500", description = "Error saving file")
    }, method = "POST")
    @PostMapping("/save")
    public ResponseEntity<GenericResponse<String>> save(@RequestParam("file") UploadRequest file) {
        var response = fileStorageService.save(file);
        if (response.getError() != null) {
            var status = response.getStatus().equals("500") ? 500 : 400;
            return ResponseEntity.status(status).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Find file", description = "Find file by name", responses = {
            @ApiResponse(responseCode = "200", description = "Image found"),
            @ApiResponse(responseCode = "400", description = "Error finding image"),
            @ApiResponse(responseCode = "500", description = "Error finding image")
    }, method = "GET")
    @GetMapping("/find")
    public ResponseEntity<GenericResponse<ImageData>> find(@RequestParam("name") String name) {
        var response = fileStorageService.findByName(name);
        if (response.getError() != null) {
            var status = response.getStatus().equals("500") ? 500 : 400;
            return ResponseEntity.status(status).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Find all files", description = "Find all files by name", responses = {
            @ApiResponse(responseCode = "200", description = "Images found"),
            @ApiResponse(responseCode = "400", description = "Error finding images"),
            @ApiResponse(responseCode = "500", description = "Error finding images")
    }, method = "GET")
    @GetMapping("/findAllIn")
    public ResponseEntity<GenericResponse<?>> findAll(@RequestParam("names") List<String> names) {
        var response = fileStorageService.findAllByNameIn(names);
        if (response.getError() != null) {
            var status = response.getStatus().equals("500") ? 500 : 400;
            return ResponseEntity.status(status).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete file", description = "Delete file by name", responses = {
            @ApiResponse(responseCode = "200", description = "File deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Error deleting file"),
            @ApiResponse(responseCode = "500", description = "Error deleting file")
    }, method = "DELETE")
    @DeleteMapping("/delete")
    public ResponseEntity<GenericResponse<?>> delete(@RequestParam("name") String name) {
        fileStorageService.deleteByName(name);
        return ResponseEntity.ok(new GenericResponse<>("Success", "File deleted successfully", "200", null));
    }
}