package com.hafsaelakhdar.springbootproject.repository;

import com.hafsaelakhdar.springbootproject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
