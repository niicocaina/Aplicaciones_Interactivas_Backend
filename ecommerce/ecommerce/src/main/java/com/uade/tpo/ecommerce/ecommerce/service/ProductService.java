package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.BasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.FavoriteRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.RecentlyViewedRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private RecentlyViewedRepository recentlyViewedRepository;

    @Autowired
    private BasketRepository basketRepository;

    public List<Product> getFeaturedProducts() {
        return productRepository.findFeaturedProducts();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getRecentlyViewedProducts(User user) {
        return productRepository.findRecentlyViewedByUser(user.getId());
    }

    public ProductDTO getProductDetail(Long productId,User user) throws Exception {

        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Producto no encontrado"));
        RecentlyViewed recent = new RecentlyViewed();
        recent.setProduct(product);
        recent.setUser(user);
        recent.setViewedAt(LocalDateTime.now());
        recentlyViewedRepository.save(recent);
        return new ProductDTO(product);
    }

    public void addToFavorites(Long productId, User user) {
        Favorite favorite = new Favorite();
        favorite.setProductId(productId);
        favorite.setUser(user);
        favoriteRepository.save(favorite);
    }

    public boolean addToBasket(Long productId, User user) throws Exception{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("PRoducto no encontrado"));

        if (product.getStock() > 0) {
            Basket basket = new Basket();
            basket.setUser(user);
            basket.setProductId(productId);
            basketRepository.save(basket);
            return true;
        }
        return false;
    }
}
