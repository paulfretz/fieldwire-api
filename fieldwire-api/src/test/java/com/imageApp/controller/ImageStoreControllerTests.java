package com.imageApp.controller;

import com.imageApp.resource.ImageResource;
import com.imageApp.service.impl.ImageStoreServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.data.domain.Pageable;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations="classpath:application-test.properties")
public class ImageStoreControllerTests {

    private MockMultipartFile file1;
    private MockMultipartFile file2;
    private MockMultipartFile file3;

    private ResponseEntity<ImageResource> response1;
    private ResponseEntity<ImageResource> response2;
    private ResponseEntity<ImageResource> response3;

    @Autowired
    ImageStoreController imageStoreController = new ImageStoreController(new ImageStoreServiceImpl());

    public void createDummyFiles() {
        file1 = new MockMultipartFile("file1.png", "file1.png", "image/png", "{\"key1\": \"value1\"}".getBytes());
        file2 = new MockMultipartFile("file2.jpg", "file2.jpg", "image/jpg", "{\"key2\": \"value2\"}".getBytes());
        file3 = new MockMultipartFile("file3.gif", "file3.gif", "image/gif", "{\"key3\": \"value3\"}".getBytes());

        response1 = imageStoreController.uploadImage(file1);
        response2 = imageStoreController.uploadImage(file2);
        response3 = imageStoreController.uploadImage(file3);
    }

    @Test
    @Order(1)
    public void uploadTest() {
        createDummyFiles();
        Assertions.assertTrue(response1.getStatusCode() == HttpStatus.CREATED);
        Assertions.assertTrue(response1.getBody().getId() == 1);
        Assertions.assertTrue(response1.getBody().getType().equals("image/png"));
        Assertions.assertTrue(response1.getBody().getName().equals("file1.png"));

        Assertions.assertTrue(response2.getStatusCode() == HttpStatus.CREATED);
        Assertions.assertTrue(response2.getBody().getId() == 2);
        Assertions.assertTrue(response2.getBody().getType().equals("image/jpg"));
        Assertions.assertTrue(response2.getBody().getName().equals("file2.jpg"));

        Assertions.assertTrue(response3.getStatusCode() == HttpStatus.CREATED);
        Assertions.assertTrue(response3.getBody().getId() == 3);
        Assertions.assertTrue(response3.getBody().getType().equals("image/gif"));
        Assertions.assertTrue(response3.getBody().getName().equals("file3.gif"));
    }

    @Test
    @Order(2)
    public void getAllTest() {
        Pageable pageable = PageRequest.of(0,10);
        final ResponseEntity<Page<ImageResource>> getAllResponse = imageStoreController.getAll(pageable);
        ImageResource first = getAllResponse.getBody().getContent().stream().findFirst().get();

        Assertions.assertTrue(getAllResponse.getStatusCode() == HttpStatus.OK);
        Assertions.assertTrue(getAllResponse.getBody().getContent().size() == 3);
        Assertions.assertTrue((first.getName().equals("file1.png")) && (first.getId() == 1) && (first.getType().equals("image/png")));
        Assertions.assertTrue(getAllResponse.getBody().getContent().stream().findFirst().get().getName().equals(("file1.png")));
    }

    @Test
    @Order(3)
    public void getImageByIdTest() {
        final ResponseEntity<ImageResource> image1resp = imageStoreController.getImageById(1);
        Assertions.assertTrue(image1resp.getStatusCode() == HttpStatus.OK);
        ImageResource image1Resource = image1resp.getBody();
        Assertions.assertTrue(image1Resource.getType().equals("image/png") && image1Resource.getId() == 1 && image1Resource.getName().equals("file1.png"));

        final ResponseEntity<ImageResource> image2resp = imageStoreController.getImageById(2);
        ImageResource image2Resource = image2resp.getBody();
        Assertions.assertTrue(image2Resource.getType().equals("image/jpg") && image2Resource.getId() == 2 && image2Resource.getName().equals("file2.jpg"));

        final ResponseEntity<ImageResource> image3resp = imageStoreController.getImageById(3);
        ImageResource image3Resource = image3resp.getBody();
        Assertions.assertTrue(image3Resource.getType().equals("image/gif") && image3Resource.getId() == 3 && image3Resource.getName().equals("file3.gif"));
    }

    @Test
    @Order(4)
    public void updateImageName(){
        final ResponseEntity<ImageResource> image1resp = imageStoreController.updateImageName(1, "newName");
        Assertions.assertTrue(image1resp.getStatusCode() == HttpStatus.OK);
        Assertions.assertTrue(image1resp.getBody().getName().equals("newName"));
        Assertions.assertTrue(image1resp.getBody().getName().equals("newName") && imageStoreController.getImageById(1).getBody().getName().equals("newName"));
    }

    @Test
    @Order(5)
    public void deleteImage(){
        final ResponseEntity<Void> deleteResp = imageStoreController.deleteImage(2);
        Assertions.assertTrue(deleteResp.getStatusCode() == HttpStatus.NO_CONTENT);

        Pageable pageable = PageRequest.of(0,10);
        Assertions.assertTrue(imageStoreController.getAll(pageable).getBody().getContent().size() == 2);

        Assertions.assertNull(imageStoreController.getImageById(2).getBody());
    }



}
