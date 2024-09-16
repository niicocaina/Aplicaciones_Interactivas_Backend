package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.CartRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.FavoriteRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Cart;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Favorite;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private CartRepository cartRepository;

    // Obtener productos destacados
    public List<Product> getFeaturedProducts() {
        return productRepository.findFeaturedProducts();
    }

    // Obtener productos por categoría
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Obtener productos vistos recientemente por el usuario
    public List<Product> getRecentlyViewedProducts(User user) {
        return productRepository.findRecentlyViewedByUser(user.getId());
    }

    // Obtener detalles del producto
    public ProductDTO getProductDetail(Long productId) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Producto no encontrado"));
        return new ProductDTO(product);
    }

    // Añadir producto a favoritos
    public void addToFavorites(Long productId, User user) {
        Favorite favorite = new Favorite();
        favorite.setProductId(productId);
        favorite.setUser(user);
        favoriteRepository.save(favorite);
    }

    // Añadir producto al carrito
    public boolean addToCart(Long productId, User user) throws Exception{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("PRoducto no encontrado"));

        if (product.getStock() > 0) {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProductId(productId);
            cartRepository.save(cart);
            return true;
        }
        return false;  // No hay stock disponible
    }
}
