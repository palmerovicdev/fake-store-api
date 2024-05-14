package com.suehay.fsastorageservice.controller;

import com.suehay.fsastorageservice.model.entity.ImageData;
import com.suehay.fsastorageservice.model.request.UploadRequest;
import com.suehay.fsastorageservice.model.response.GenericResponse;
import com.suehay.fsastorageservice.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "File Storage", description = "File storage controller")
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
    public ResponseEntity<GenericResponse<String>> save(
            @Parameter(description = "File to save")
            @RequestParam("file") @NotNull UploadRequest file) {
        var response = fileStorageService.save(file);
        if (response.getError() != null) {
            var status = response.getStatus().equals("500") ? 500 : 400;
            return ResponseEntity.status(status).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get file", description = "Get file by name", responses = {
            @ApiResponse(responseCode = "200", description = "Image found"),
            @ApiResponse(responseCode = "400", description = "Error finding image"),
            @ApiResponse(responseCode = "500", description = "Error finding image")
    }, method = "GET")
    @GetMapping("/get")
    public ResponseEntity<GenericResponse<ImageData>> get(
            @Parameter(description = "File name")
            @RequestParam("name") @NotNull String name) {
        var response = fileStorageService.getByName(name);
        if (response.getError() != null) {
            var status = response.getStatus().equals("500") ? 500 : 400;
            return ResponseEntity.status(status).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all files", description = "Get all files by name", responses = {
            @ApiResponse(responseCode = "200", description = "Images found"),
            @ApiResponse(responseCode = "400", description = "Error finding images"),
            @ApiResponse(responseCode = "500", description = "Error finding images")
    }, method = "GET")
    @GetMapping("/getAllIn")
    public ResponseEntity<GenericResponse<?>> getAll(
            @Parameter(description = "File names")
            @RequestParam("names") @NotNull List<String> names) {
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
    public ResponseEntity<GenericResponse<?>> delete(
            @Parameter(description = "File name")
            @RequestParam("name") @NotNull String name) {
        fileStorageService.deleteByName(name);
        return ResponseEntity.ok(new GenericResponse<>("Success", "File deleted successfully", "200", null));
    }
}