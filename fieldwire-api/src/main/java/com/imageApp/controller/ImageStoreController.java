package com.imageApp.controller;

import com.imageApp.controller.searchOptions.ImageSearchOptions;
import com.imageApp.resource.ImageResource;
import com.imageApp.service.ImageStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/")
public class ImageStoreController {

    @Autowired
    final private ImageStoreService imageStoreService;

    public ImageStoreController(ImageStoreService imageStoreService) {
        this.imageStoreService = imageStoreService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<ImageResource> uploadImage(@RequestParam MultipartFile file) {
        return new ResponseEntity<>(imageStoreService.uploadImage(file), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<Page<ImageResource>> getAll(@PageableDefault(sort = {"id"}) Pageable pageable){
        return new ResponseEntity<>(imageStoreService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/searchByName")
    public ResponseEntity<Page<ImageResource>> getAllByName(@PageableDefault(sort = {"id"}) Pageable pageable, ImageSearchOptions options){
        return new ResponseEntity<>(imageStoreService.getAllByName(options, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ImageResource> getImageById(@PathVariable int id) {
        return new ResponseEntity<>(imageStoreService.getImageById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}/{name}")
    public ResponseEntity<ImageResource> updateImageName(@PathVariable int id, @PathVariable String name){
        return new ResponseEntity<>(imageStoreService.updateImage(id, name),HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable int id){
        imageStoreService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
