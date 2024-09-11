package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
public class ProductDTO {

    private Long productId;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;

    private Category category;

    private List imgUrl;


}