package com.hafsaelakhdar.springbootproject.repository;

import com.hafsaelakhdar.springbootproject.entities.Order;
import com.hafsaelakhdar.springbootproject.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByUserIdAndOrderStatus(Integer userId, OrderStatus orderStatus);


    List<Order> findAllByUserIdAndOrderStatus(Integer userId, OrderStatus orderStatus);

    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
}
