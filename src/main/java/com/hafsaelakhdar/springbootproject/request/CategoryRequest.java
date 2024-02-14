package com.hafsaelakhdar.springbootproject.request;


import lombok.Data;

@Data
public class CategoryRequest {
    private long id;

    private String name;

    private String description;
}
