package com.hafsaelakhdar.springbootproject.service;

import com.hafsaelakhdar.springbootproject.entities.Category;
import com.hafsaelakhdar.springbootproject.entities.Product;
import com.hafsaelakhdar.springbootproject.request.CategoryRequest;
import com.hafsaelakhdar.springbootproject.request.OrderRequest;
import com.hafsaelakhdar.springbootproject.request.ProductRequest;
import com.hafsaelakhdar.springbootproject.request.UserRequest;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;


public interface AdminService {
    Category createCategory(CategoryRequest category);

    List<CategoryRequest> getAllCategories();

    void deleteCategory(Long id);

    Product createProduct(ProductRequest productRequest, MultipartFile image, Long categoryId) throws IOException;

    List<ProductRequest> getAllProducts();

    void deleteProduct(Long id);

    ProductRequest getProductById(Long id);
    ProductRequest updateProduct(MultipartFile image, Long categoryId, Long productId , ProductRequest productRequest) throws IOException;

    List<UserRequest> getAllUsers();

    List<OrderRequest> getAllOrders();
}
