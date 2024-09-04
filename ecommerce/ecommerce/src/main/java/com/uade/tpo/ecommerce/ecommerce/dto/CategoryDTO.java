package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
public class CategoryDTO {

        private Long id;
        private String description;
        private List<Product> productList;

    }

