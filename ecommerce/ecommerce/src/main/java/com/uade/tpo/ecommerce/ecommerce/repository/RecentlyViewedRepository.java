package com.uade.tpo.ecommerce.ecommerce.repository;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.RecentlyViewed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecentlyViewedRepository extends JpaRepository<RecentlyViewed, Long> {

    // Obtener los productos vistos recientemente por un usuario
    @Query("SELECT rv.product FROM RecentlyViewed rv WHERE rv.user.id = :userId ORDER BY rv.viewedAt DESC")
    List<Product> findRecentlyViewedByUser(@Param("userId") Long userId);
}
