package com.hafsaelakhdar.springbootproject.service;

import com.hafsaelakhdar.springbootproject.request.CartItemRequest;
import com.hafsaelakhdar.springbootproject.request.OrderRequest;
import com.hafsaelakhdar.springbootproject.request.PlaceOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

    ResponseEntity<?> addProductToCart(CartItemRequest cartItemRequest);

    OrderRequest getCartByUserId(Integer userId);

    OrderRequest addMinusOnProduct(Integer userId, Long productId);

    OrderRequest addPlusOnProduct(Integer userId, Long productId);

    OrderRequest placeOrder(PlaceOrderRequest placeOrderRequest);

    List<OrderRequest> getOrdersByUserId(Integer userId);

}