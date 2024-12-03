package com.uade.tpo.ecommerce.ecommerce.dto;


import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.ProductBasket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BasketDTO {
    private Long basketId;
    private Date creationDate;
    private UserDTO user;
    private List<ProductBasketDTO> products;

    public BasketDTO() {}

    public Basket toBasket() {
        Basket basket = new Basket();
        basket.setId(this.basketId);
        basket.setCreationDate(this.creationDate);
        basket.setUser(this.user.toUser());

        List<ProductBasket> productBasketList = new ArrayList<>();
        for (ProductBasketDTO productBasketDTO : this.products) {
            ProductBasket productBasket = productBasketDTO.toProductBasket();
            productBasketList.add(productBasket);
        }
        basket.setProducts(productBasketList);
        return basket;
    }
}
