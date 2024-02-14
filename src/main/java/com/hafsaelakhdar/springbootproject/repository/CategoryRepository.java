package com.hafsaelakhdar.springbootproject.repository;

import com.hafsaelakhdar.springbootproject.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Optional<Category> findAllById(Long categoryId);
}
