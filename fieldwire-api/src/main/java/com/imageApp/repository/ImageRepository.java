package com.imageApp.repository;

import com.imageApp.resource.ImageResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<ImageResource, Integer> {
    ImageResource findById(int id);

    default Page<ImageResource> getAll(Pageable pageable){
        return findAll(pageable);
    }
}
