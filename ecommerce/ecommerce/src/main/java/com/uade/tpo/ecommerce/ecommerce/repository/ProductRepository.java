package com.uade.tpo.ecommerce.ecommerce.repository;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
