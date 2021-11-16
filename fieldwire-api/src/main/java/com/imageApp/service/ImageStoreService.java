package com.imageApp.service;

import com.imageApp.resource.ImageResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ImageStoreService {

        Page<ImageResource> getAll(Pageable pageable);

        ImageResource uploadImage(MultipartFile image);

        ImageResource getImageById(int imageId);

        ImageResource updateImage(int imageId, String newName);

        void deleteImage(int imageId);
}
