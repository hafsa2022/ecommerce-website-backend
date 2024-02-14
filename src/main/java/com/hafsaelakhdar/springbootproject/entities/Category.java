package com.hafsaelakhdar.springbootproject.entities;

import com.hafsaelakhdar.springbootproject.request.CategoryRequest;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String description;

    public CategoryRequest getCategoryDTO(){
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(id);
        categoryRequest.setName(name);
        categoryRequest.setDescription(description);
        return categoryRequest;
    }

}
