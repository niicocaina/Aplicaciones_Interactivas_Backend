package com.uade.tpo.ecommerce.ecommerce.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketRequest {
    Long productId;
    int quantity;
}
