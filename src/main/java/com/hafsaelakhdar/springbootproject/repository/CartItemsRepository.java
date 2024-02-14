package com.hafsaelakhdar.springbootproject.repository;

import com.hafsaelakhdar.springbootproject.entities.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
    Optional<CartItems> findByUserIdAndProductIdAndOrderId(Integer userId, Long productId, Long orderId);
}
