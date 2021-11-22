package com.imageApp.service.impl;

import com.imageApp.controller.searchOptions.ImageSearchOptions;
import com.imageApp.repository.ImageRepository;
import com.imageApp.resource.ImageResource;
import com.imageApp.service.ImageStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageStoreServiceImpl implements ImageStoreService {

    @Autowired
    public ImageRepository imageRepository;

    public ImageResource uploadImage(MultipartFile image){
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());

        try {
            ImageResource imageResource = new ImageResource((int)Math.random(), fileName, image.getContentType(), image.getBytes());
            return imageRepository.save(imageResource);

        } catch (IOException e) {
            System.out.println("Error");
        }
        return null;
    }

    public Page<ImageResource> getAll(Pageable pageable){
        return imageRepository.getAll(pageable);
    }

    public Page<ImageResource> getAllByName(ImageSearchOptions options, Pageable pageable){
        if(ObjectUtils.isEmpty(options)){
            return imageRepository.getAll(pageable);
        } else {
            return imageRepository.getAllByName(options, pageable);
        }
    }

    public ImageResource getImageById (int imageId){
        ImageResource image = imageRepository.findById(imageId);
        return image;
    }

    public ImageResource updateImage(int imageId, String newName){
        ImageResource image = imageRepository.findById(imageId);
        image.setName(newName);
        imageRepository.save(image);
        return image;
    }

    public void deleteImage(int imageId){
        imageRepository.delete(imageRepository.findById(imageId));
    }
}
