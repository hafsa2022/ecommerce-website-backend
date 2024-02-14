package com.hafsaelakhdar.springbootproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hafsaelakhdar.springbootproject.request.CategoryRequest;
import com.hafsaelakhdar.springbootproject.request.ProductRequest;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name="products")
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String name;

    //@Column(columnDefinition = "TEXT")
    @Lob
    private String description;

    private Integer price;

    //@Lob
    //@Column(name = "image",columnDefinition = "LONGBLOB")
    //private byte[] image;
    @Lob
    private byte[] image;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id" , nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;


    public ProductRequest getProductDTO(){
        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(id);
        productRequest.setName(name);
        productRequest.setDescription(description);
        productRequest.setPrice(price);
        productRequest.setReturnedImage(image);
        productRequest.setCategoryId(category.getId());
        productRequest.setCategoryName(category.getName());
        return productRequest;
    }
}
