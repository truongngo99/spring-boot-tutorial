package com.example.demoSpring.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.demoSpring.models.RepositoryObject;
import com.example.demoSpring.services.IStorageService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {

    @Autowired
    private IStorageService iStorageService;

    // This is controller receive file/image from client
    @PostMapping("")
    ResponseEntity<RepositoryObject> uploadFile(@RequestParam MultipartFile file) {
        try {
            // save file to a folder
            String generatedFileName = iStorageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new RepositoryObject("ok", "upload file successfully", generatedFileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new RepositoryObject("failed", e.getMessage(), ""));
        }
    }

    @GetMapping("files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = iStorageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    // get all image
    @GetMapping("")
    public ResponseEntity<RepositoryObject> getUploadedFiles() {
        try {
            List<String> paths = iStorageService.loadAll()
                    .map(path -> {
                        String urlPaths = MvcUriComponentsBuilder
                                .fromMethodName(FileUploadController.class, "readDetailFile", path.getFileName().toString())
                                .build().toUri().toString();
                        return urlPaths;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok()
                    .body(new RepositoryObject("ok", "List files successfully", paths));
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(new RepositoryObject("ok", "List files successfully", new String[] {}));
        }
    }

     @DeleteMapping("")
    public ResponseEntity<RepositoryObject> deleteAllFiles() {
        try {
            iStorageService.deleteAllFiles();
            return ResponseEntity.ok()
                    .body(new RepositoryObject("ok", "Delete all file successfully", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new RepositoryObject("failed", e.getMessage(), ""));
        }
    }
    

}
