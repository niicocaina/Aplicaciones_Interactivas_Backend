package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import com.uade.tpo.ecommerce.ecommerce.dto.UserDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import com.uade.tpo.ecommerce.ecommerce.service.ProductService;
import com.uade.tpo.ecommerce.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@RestController
@RequestMapping("api/v1/catalog")
public class CatalogController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    // Listado de productos destacados
    @GetMapping("/featured")
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        List<Product> products = productService.getFeaturedProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Listado de productos por categoría
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Listado de productos vistos recientemente
    @GetMapping("/recent")
    public ResponseEntity<List<Product>> getRecentlyViewedProducts() throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user_final = userService.getUserByEmail(auth.getName());
        User user = user_final.toUser();
        List<Product> products = productService.getRecentlyViewedProducts(user);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Detalles del producto
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDTO> getProductDetail(@PathVariable Long productId) throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user_final = userService.getUserByEmail(auth.getName());
        User user = user_final.toUser();;
        ProductDTO productDetail = productService.getProductDetail(productId,user);
        return new ResponseEntity<>(productDetail, HttpStatus.OK);
    }

    // Agregar producto a favoritos
    @PostMapping("/product/{productId}/favorite")
    public ResponseEntity<Void> addToFavorites(@PathVariable Long productId) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user_final = userService.getUserByEmail(auth.getName());
        User user = user_final.toUser();
        productService.addToFavorites(productId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

