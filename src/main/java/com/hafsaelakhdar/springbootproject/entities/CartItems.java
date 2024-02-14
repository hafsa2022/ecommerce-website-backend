package com.hafsaelakhdar.springbootproject.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hafsaelakhdar.springbootproject.request.CartItemRequest;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price;

    private  Long quantity;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @OneToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "order_id")
    private Order order;

    public CartItemRequest getCartItemsDto(){
        CartItemRequest cartItemRequest = new CartItemRequest();
        cartItemRequest.setId(id);
        cartItemRequest.setProductId(product.getId());
        cartItemRequest.setPrice(price);
        cartItemRequest.setQuantity(quantity);
        cartItemRequest.setProductName(product.getName());
        cartItemRequest.setReturnedImg(product.getImage());
        cartItemRequest.setUserId(user.getId());
        return cartItemRequest;
    }
}
