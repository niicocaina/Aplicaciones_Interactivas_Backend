package com.uade.tpo.ecommerce.ecommerce.repository;


import com.uade.tpo.ecommerce.ecommerce.repository.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckOutRepository extends JpaRepository<CheckOut, Long> {

    List<CheckOut> findByUserId(Long userId);
}
