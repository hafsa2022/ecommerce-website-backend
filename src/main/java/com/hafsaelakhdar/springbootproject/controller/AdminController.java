package com.hafsaelakhdar.springbootproject.controller;

import com.hafsaelakhdar.springbootproject.entities.Category;
import com.hafsaelakhdar.springbootproject.entities.Product;
import com.hafsaelakhdar.springbootproject.request.CategoryRequest;
import com.hafsaelakhdar.springbootproject.request.OrderRequest;
import com.hafsaelakhdar.springbootproject.request.ProductRequest;
import com.hafsaelakhdar.springbootproject.request.UserRequest;
import com.hafsaelakhdar.springbootproject.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @PostMapping("/addcategory")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryRequest request) {

        Category createdCategory = adminService.createCategory(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping("/getcategories")
    public ResponseEntity<List<CategoryRequest>> getAllCategories(){
        List<CategoryRequest> allCategories =  adminService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @DeleteMapping("/deletecategory/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        adminService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value="/addproduct/{categoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> createProduct(@RequestParam(name="image") MultipartFile image, @RequestPart(name="product") ProductRequest productRequest, @PathVariable Long categoryId ) throws IOException {
        Product createdProduct = adminService.createProduct(productRequest, image, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/getproducts")
    public ResponseEntity<List<ProductRequest>> getAllProducts(){
        List<ProductRequest> allProducts =  adminService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @DeleteMapping("/deleteproduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        adminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductRequest> getProductById(@PathVariable Long id){
        ProductRequest productRequest = adminService.getProductById(id);
        if(productRequest == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productRequest);
    }

    @PutMapping(value="/{categoryId}/updateproduct/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<ProductRequest> updateProduct(@RequestParam(name="image") MultipartFile image, @RequestPart(name="product") ProductRequest productRequest, @PathVariable Long categoryId, @PathVariable Long productId) throws IOException {
        ProductRequest updatedProduct = adminService.updateProduct(image, categoryId, productId, productRequest);
        return ResponseEntity.ok(updatedProduct);
        }

    @GetMapping("/getusers")
    public ResponseEntity<List<UserRequest>> getAllUsers(){
        List<UserRequest> allUsers =  adminService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/orders")
    public  ResponseEntity<List<OrderRequest>> getAllOrders(){
        List<OrderRequest> ordersList = adminService.getAllOrders();
        if(ordersList == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ordersList);
    }
}