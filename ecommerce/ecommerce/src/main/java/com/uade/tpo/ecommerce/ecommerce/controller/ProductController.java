package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import com.uade.tpo.ecommerce.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@CrossOrigin(origins = "http://localhost:3030")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) throws Exception {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() throws Exception {
        List<ProductDTO> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createProduct(@RequestBody ProductDTO productDTO) throws Exception {
        productService.saveNewProduct(productDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/featured/{productId}")
    public ResponseEntity<Void> addToFeatured(@PathVariable Long productId) throws Exception{
        productService.addToFeatured(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/featured/{productId}")
    public ResponseEntity<Void> removeFeatured(@PathVariable Long productId) throws Exception{
        productService.removeFeatured(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) throws Exception{
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/{newStock}")
    public ResponseEntity<HttpStatus> updateStock(@PathVariable Long id, @PathVariable Integer newStock) throws Exception{
        productService.updateStockById(id, newStock);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) throws Exception{
        productService.updateProductById(id, productDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
