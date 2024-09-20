package com.uade.tpo.ecommerce.ecommerce.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BasketSummaryDTO {
    Date creationDate;
    List<ProductInBasketDTO> products;
}
