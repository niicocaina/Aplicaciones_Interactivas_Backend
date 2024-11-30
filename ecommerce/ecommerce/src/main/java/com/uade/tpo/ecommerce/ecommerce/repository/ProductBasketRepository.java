package com.uade.tpo.ecommerce.ecommerce.repository;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.ProductBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductBasketRepository extends JpaRepository<ProductBasket, Long> {
    List<ProductBasket> findByBasket(Basket basket);
}
