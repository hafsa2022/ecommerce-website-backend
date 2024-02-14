package com.hafsaelakhdar.springbootproject.service.impl;


import com.hafsaelakhdar.springbootproject.entities.*;
import com.hafsaelakhdar.springbootproject.repository.CartItemsRepository;
import com.hafsaelakhdar.springbootproject.repository.OrderRepository;
import com.hafsaelakhdar.springbootproject.repository.ProductRepository;
import com.hafsaelakhdar.springbootproject.repository.UserRepository;
import com.hafsaelakhdar.springbootproject.request.CartItemRequest;
import com.hafsaelakhdar.springbootproject.request.OrderRequest;
import com.hafsaelakhdar.springbootproject.request.PlaceOrderRequest;
import com.hafsaelakhdar.springbootproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemsRepository cartItemsRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public ResponseEntity<?> addProductToCart(CartItemRequest cartItemRequest){

        Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(cartItemRequest.getUserId(), OrderStatus.PENDING);
        System.out.println("order_id" + pendingOrder);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(cartItemRequest.getUserId(),cartItemRequest.getProductId(),cartItemRequest.getOrderId());
        if(optionalCartItems.isPresent()){
            CartItemRequest productAlreadyExistInCart = new CartItemRequest();
            productAlreadyExistInCart.setProductId(null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(productAlreadyExistInCart);
        }else{
            Optional<Product> optionalProduct = productRepository.findById(cartItemRequest.getProductId());
            Optional<User> optionalUser = userRepository.findById(cartItemRequest.getUserId());
            if(optionalProduct.isPresent() && optionalUser.isPresent()){
                CartItems cartItem = new CartItems();
                Product product = optionalProduct.get();
                cartItem.setProduct(product);
                cartItem.setUser(optionalUser.get());
                cartItem.setQuantity(1L);
                cartItem.setOrder(pendingOrder);
                cartItem.setPrice(product.getPrice());
                CartItems updateCart = cartItemsRepository.save(cartItem);
                //Long totalPrice = cartItem.getPrice() * cartItem.getQuantity();
                Integer totalPrice = cartItem.getPrice();
                pendingOrder.setPrice(pendingOrder.getPrice() + totalPrice);
                pendingOrder.getCartItems().add(cartItem);
                orderRepository.save(pendingOrder);
                CartItemRequest updateCartItemRequest = new CartItemRequest();
                updateCartItemRequest.setId(cartItem.getId());
                return  ResponseEntity.status(HttpStatus.CREATED).body(updateCartItemRequest);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product or User not found");
            }
        }

    }
    @Override
    public OrderRequest getCartByUserId(Integer userId){
        Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId,OrderStatus.PENDING);
        List<CartItemRequest> cartItemsList = pendingOrder.getCartItems().stream().map(CartItems::getCartItemsDto).toList();
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setCartItemRequestList(cartItemsList);
        orderRequest.setAmount(pendingOrder.getPrice());
        orderRequest.setId(pendingOrder.getId());
        orderRequest.setOrderStatus(pendingOrder.getOrderStatus());
       return orderRequest;
    }

    @Override
    public OrderRequest addMinusOnProduct(Integer userId, Long productId) {
        Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId,OrderStatus.PENDING);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId,productId, pendingOrder.getId());
        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItem = optionalCartItems.get();
            cartItem.setQuantity(cartItem.getQuantity() - 1 );
            pendingOrder.setPrice(pendingOrder.getPrice() - optionalProduct.get().getPrice());
            cartItemsRepository.save(cartItem);
            orderRepository.save(pendingOrder);
            return pendingOrder.getOrderDto();

        }
        return null;
    }
    @Override
    public OrderRequest addPlusOnProduct(Integer userId, Long productId){
        Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId,OrderStatus.PENDING);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId,productId, pendingOrder.getId());
        if(optionalProduct.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItem = optionalCartItems.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1 );
            pendingOrder.setPrice(pendingOrder.getPrice() + optionalProduct.get().getPrice());
            cartItemsRepository.save(cartItem);
            orderRepository.save(pendingOrder);
            return pendingOrder.getOrderDto();

        }
        return null;
    }

    @Override
    public OrderRequest placeOrder(PlaceOrderRequest placeOrderRequest) {
        Order existingOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderRequest.getUserId(),OrderStatus.PENDING);
        Optional<User> optionalUser = userRepository.findById(placeOrderRequest.getUserId());
        if(optionalUser.isPresent()){
            existingOrder.setOrderStatus(OrderStatus.SUBMITTED);
            existingOrder.setAddress(placeOrderRequest.getAddress());
            existingOrder.setDate(new Date());
            existingOrder.setPaymentType(placeOrderRequest.getPayment());
            existingOrder.setDescription(placeOrderRequest.getOrderDescription());
            existingOrder.setPrice(existingOrder.getPrice());
            orderRepository.save(existingOrder);
            Order order = new Order();
            order.setOrderStatus(OrderStatus.PENDING);
            order.setPrice(0L);
            order.setUser(optionalUser.get());
            orderRepository.save(order);
            return order.getOrderDto();
        }
        return null;
    }

    @Override
    public List<OrderRequest> getOrdersByUserId(Integer userId) {
        return orderRepository.findAllByUserIdAndOrderStatus(userId,OrderStatus.SUBMITTED).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

}