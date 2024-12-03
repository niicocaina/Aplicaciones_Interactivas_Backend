package com.uade.tpo.ecommerce.ecommerce.dto;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.ProductBasket;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class ProductBasketDTO {
    private Long productBasketId;
    private int quantity;
    private BasketDTO basket;
    private ProductDTO product;

    public ProductBasketDTO() {}

    public ProductBasket toProductBasket() {
        ProductBasket productBasket = new ProductBasket();
        productBasket.setProductBasketId(this.productBasketId);
        productBasket.setQuantity(this.quantity);
        productBasket.setBasket(this.basket.toBasket());
        productBasket.setProduct(this.product.toProduct());
        return productBasket;
    }
}
