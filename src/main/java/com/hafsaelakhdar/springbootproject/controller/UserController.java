package com.hafsaelakhdar.springbootproject.controller;

import com.hafsaelakhdar.springbootproject.request.CartItemRequest;
import com.hafsaelakhdar.springbootproject.request.OrderRequest;
import com.hafsaelakhdar.springbootproject.request.PlaceOrderRequest;
import com.hafsaelakhdar.springbootproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/addproducttocart")
    public ResponseEntity<?> addProductToCart(@RequestBody CartItemRequest cartItemRequest){
       return userService.addProductToCart(cartItemRequest);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Integer userId){
        OrderRequest orderRequest = userService.getCartByUserId(userId);
        if(orderRequest == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderRequest);
    }

    @GetMapping("/{userId}/deduct/{productId}")
    public ResponseEntity<OrderRequest> addMinusOnProduct(@PathVariable Integer userId, @PathVariable Long productId){
        OrderRequest orderRequest = userService.addMinusOnProduct(userId, productId);
        if(orderRequest==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderRequest);
    }

    @GetMapping("/{userId}/add/{productId}")
    public ResponseEntity<OrderRequest> addPlusOnProduct(@PathVariable Integer userId, @PathVariable Long productId){
        OrderRequest orderRequest = userService.addPlusOnProduct(userId, productId);
        if(orderRequest==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderRequest);
    }

    @PostMapping("/placeorder")
    public ResponseEntity<OrderRequest> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest){
        OrderRequest orderRequest = userService.placeOrder(placeOrderRequest);
        if(orderRequest == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRequest);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<OrderRequest>> getOrdersByUserId(@PathVariable Integer userId){
        List<OrderRequest> ordersList = userService.getOrdersByUserId(userId);
        if(ordersList == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ordersList);
    }



}
