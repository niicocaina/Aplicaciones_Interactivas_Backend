package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import com.uade.tpo.ecommerce.ecommerce.dto.CheckOutDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class CheckOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkOutId;
    private Date transactionDate;
    private double total;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany
    private List<ProductBasket> products;

    public CheckOutDTO toDTO() {
        CheckOutDTO dto = new CheckOutDTO();
        dto.setCheckOutId(this.checkOutId);
        dto.setTransactionDate(this.transactionDate);
        dto.setTotal(this.total);
        dto.setUser(this.user);
        dto.setProducts(this.products);
        return dto;
    }
}
