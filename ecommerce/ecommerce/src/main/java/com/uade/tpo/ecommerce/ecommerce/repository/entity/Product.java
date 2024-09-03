package com.uade.tpo.ecommerce.ecommerce.repository.entity;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
=======
import jakarta.persistence.*;

import java.awt.*;
import java.lang.reflect.Array;
>>>>>>> 80020acd54048544f255adb592d94ba952787982

@Entity
public class Product {
    @Id
<<<<<<< HEAD
    private long id;
}
=======
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private int price;
    private int promotionalPrice;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private List imgUrl;

}
>>>>>>> 80020acd54048544f255adb592d94ba952787982
