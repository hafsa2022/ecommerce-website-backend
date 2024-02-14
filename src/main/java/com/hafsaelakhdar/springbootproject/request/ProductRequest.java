package com.hafsaelakhdar.springbootproject.request;


import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class ProductRequest {

    private long id;

    private String name;

    private String description;

    private Integer price;

    private MultipartFile image;

    @Lob
    private byte[] returnedImage;

    private String categoryName;

    private Long categoryId;


}
