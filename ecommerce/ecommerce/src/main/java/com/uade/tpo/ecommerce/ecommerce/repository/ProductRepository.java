package com.uade.tpo.ecommerce.ecommerce.repository;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Favorite;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.featured = true")
    List<Product> findFeaturedProducts();

    List<Product> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p JOIN RecentlyViewed rv ON p.productId = rv.product.productId WHERE rv.user.id = :userId")
    List<Product> findRecentlyViewedByUser(@Param("userId") Long userId);

}

