package com.uade.tpo.ecommerce.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDTO {
 private Long id;
 private String name;
 private String email;
 private String password;
 private String firstName;
 private String lastName;
 private Role role;
}
