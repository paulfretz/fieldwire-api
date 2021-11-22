package com.imageApp.repository;

import com.imageApp.controller.searchOptions.ImageSearchOptions;
import com.imageApp.controller.searchOptions.ImageSearchOptionsSpec;
import com.imageApp.resource.ImageResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends PagingAndSortingRepository<ImageResource, Integer>, JpaRepository<ImageResource,Integer>, JpaSpecificationExecutor<ImageResource> {

    ImageResource findById(int id);

    default Page<ImageResource> getAll(Pageable pageable){
        return findAll(pageable);
    }

    default Page<ImageResource> getAllByName(ImageSearchOptions options, Pageable pageable){
        return findAll(new ImageSearchOptionsSpec(options), pageable);
    }
}
