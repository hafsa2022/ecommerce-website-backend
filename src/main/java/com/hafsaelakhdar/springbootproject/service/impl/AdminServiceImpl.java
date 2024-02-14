package com.hafsaelakhdar.springbootproject.service.impl;

import com.hafsaelakhdar.springbootproject.entities.*;
import com.hafsaelakhdar.springbootproject.repository.CategoryRepository;
import com.hafsaelakhdar.springbootproject.repository.OrderRepository;
import com.hafsaelakhdar.springbootproject.repository.ProductRepository;
import com.hafsaelakhdar.springbootproject.repository.UserRepository;
import com.hafsaelakhdar.springbootproject.request.CategoryRequest;
import com.hafsaelakhdar.springbootproject.request.OrderRequest;
import com.hafsaelakhdar.springbootproject.request.ProductRequest;
import com.hafsaelakhdar.springbootproject.request.UserRequest;
import com.hafsaelakhdar.springbootproject.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    public Category createCategory (CategoryRequest request){
        Category createdCategory = new Category();
        createdCategory.setName(request.getName());
        createdCategory.setDescription(request.getDescription());
        return categoryRepository.save(createdCategory);
    }

    public List<CategoryRequest> getAllCategories(){

        return categoryRepository.findAll().stream().map(Category::getCategoryDTO).collect(Collectors.toList());
    }

    public void deleteCategory(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new IllegalArgumentException("no category with this id");
        }
        categoryRepository.deleteById(id);
    }

    @Transactional
    public Product createProduct(ProductRequest productRequest, MultipartFile image, Long categoryId) throws IOException {
        Optional<Category> category = categoryRepository.findAllById(categoryId);
        if(category.isPresent()){
            Product createdProduct = new Product();
            createdProduct.setName(productRequest.getName());
            createdProduct.setDescription(productRequest.getDescription());
            createdProduct.setPrice(productRequest.getPrice());
            createdProduct.setCategory(category.get());

            if (image != null) {
                createdProduct.setImage(image.getBytes());
            }
            return productRepository.save(createdProduct);
        }
        return null;
    }

    public List<ProductRequest> getAllProducts(){
        return productRepository.findAll().stream().map(Product::getProductDTO).collect(Collectors.toList());
    }

    public void deleteProduct(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            throw new IllegalArgumentException("no product with this id");
        }
        productRepository.deleteById(id);
    }

    public ProductRequest getProductById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            return null;
        }
        Product product = optionalProduct.get();
        return product.getProductDTO();
    }


    public ProductRequest updateProduct(MultipartFile image, Long categoryId, Long productId , ProductRequest productRequest) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent() && optionalCategory.isPresent()){
            Product product = optionalProduct.get();
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setCategory(optionalCategory.get());

            if(image != null){
                product.setImage(image.getBytes());
            }
            Product updatedProduct = productRepository.save(product);
            ProductRequest productRequestupdated = new ProductRequest();
            productRequestupdated.setId(updatedProduct.getId());
            return productRequestupdated;
        }
        return null;
    }
    public List<UserRequest> getAllUsers(){

        return userRepository.findAll().stream().map(User::getUserDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderRequest> getAllOrders() {
        return orderRepository.findAllByOrderStatus(OrderStatus.SUBMITTED).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }



}
