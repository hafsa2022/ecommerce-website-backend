package com.hafsaelakhdar.springbootproject.request;

import lombok.Data;

@Data
public class PlaceOrderRequest {

    private Integer userId;

    private String address;

    private String orderDescription;

    private String payment;

}
