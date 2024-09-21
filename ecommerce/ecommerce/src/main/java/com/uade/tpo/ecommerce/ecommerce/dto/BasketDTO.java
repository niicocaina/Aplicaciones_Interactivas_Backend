package com.uade.tpo.ecommerce.ecommerce.dto;


import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.ProductBasket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Data
public class BasketDTO {
    private Long basketId;
    private Date creationDate;
    private User user;
    private List<ProductBasket> products;

    public BasketDTO() {}

}
