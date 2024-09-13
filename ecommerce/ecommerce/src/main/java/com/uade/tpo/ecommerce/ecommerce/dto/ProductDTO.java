package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;

@Data
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;
    private Category category;
    private List imgUrl;


}