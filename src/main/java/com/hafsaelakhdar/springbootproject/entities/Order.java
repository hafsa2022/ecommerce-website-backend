package com.hafsaelakhdar.springbootproject.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hafsaelakhdar.springbootproject.request.OrderRequest;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String address;

    private String paymentType;

    private Date date;

    private Long price;

    private OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.MERGE  )
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItems> cartItems;

    public OrderRequest getOrderDto() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(id);
        orderRequest.setOrderStatus(orderStatus);
        orderRequest.setAmount(price);
        orderRequest.setPaymentType(paymentType);
        orderRequest.setUsername(user.getUsername());
        orderRequest.setAddress(address);
        orderRequest.setDate(date);
        orderRequest.setOrderDescription(description);

        return orderRequest;
    }
}
