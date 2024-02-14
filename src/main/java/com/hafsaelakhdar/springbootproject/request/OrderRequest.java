package com.hafsaelakhdar.springbootproject.request;

import com.hafsaelakhdar.springbootproject.entities.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequest {
    private String orderDescription;

    private List<CartItemRequest> cartItemRequestList;

    private Long id ;

    private Date date;

    private Long amount;

    private String address;

    private OrderStatus orderStatus;

    private  String paymentType;

    private String username;

}
