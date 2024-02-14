package com.hafsaelakhdar.springbootproject.request;

import lombok.Data;

@Data
public class CartItemRequest {
    private Long id;

    private Integer price;

    private  Long quantity;

    private Long productId;

    private Long orderId;

    private  String productName;

    private byte[] returnedImg;

    private Integer userId;
}
