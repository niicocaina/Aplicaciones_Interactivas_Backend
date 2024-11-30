package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class ContactForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String problem;
    private String description;

    private String photoPaths;

    @ManyToOne
    private User user;


}
