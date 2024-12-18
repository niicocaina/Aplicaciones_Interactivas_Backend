package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import com.uade.tpo.ecommerce.ecommerce.dto.UserDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import com.uade.tpo.ecommerce.ecommerce.service.FavoriteService;
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
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3030")
@RequestMapping("api/v1/catalog")
public class CatalogController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    // Listado de productos destacados
    @GetMapping("/featured")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<List<Product>> getFeaturedProducts() {
        List<Product> products = productService.getFeaturedProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<Map<String, List<Product>>> getProductsGroupedByCategory() {
        Map<String, List<Product>> productsGroupedByCategory = productService.getProductsGroupedByCategory();
        return new ResponseEntity<>(productsGroupedByCategory, HttpStatus.OK);
    }

    // Listado de productos por categoría
    @GetMapping("/category/{categoryId}")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Listado de productos vistos recientemente
    @GetMapping("/recent")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<List<Product>> getRecentlyViewedProducts() throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user_final = userService.getUserByEmail(auth.getName());
        User user = user_final.toUser();
        List<Product> products = productService.getRecentlyViewedProducts(user);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Detalles del producto
    @GetMapping("/product/{productId}")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<ProductDTO> getProductDetail(@PathVariable Long productId) throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user_final = userService.getUserByEmail(auth.getName());
        User user = user_final.toUser();;
        ProductDTO productDetail = productService.getProductDetail(productId,user);
        return new ResponseEntity<>(productDetail, HttpStatus.OK);
    }

    // Agregar producto a favoritos
    @PostMapping("/product/{productId}/favorite")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<Void> addToFavorites(@PathVariable Long productId) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user_final = userService.getUserByEmail(auth.getName());
        User user = user_final.toUser();
        favoriteService.addToFavorites(productId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/product/{productId}/favorite")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<Void> removeFavorites(@PathVariable Long productId) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user_final = userService.getUserByEmail(auth.getName());
        User user = user_final.toUser();
        favoriteService.removeProductFromFavorites(user, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/favorites")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<List<Product>> getFavoritesProducts() throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user_final = userService.getUserByEmail(auth.getName());
        User user = user_final.toUser();
        List<Product> products = favoriteService.getFavoritesForUser(user);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}

