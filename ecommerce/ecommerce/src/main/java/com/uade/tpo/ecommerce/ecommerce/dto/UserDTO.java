package com.uade.tpo.ecommerce.ecommerce.dto;


import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Role;

import java.util.List;

@Data
public class UserDTO {
 private Long id;
 private String email;
 private String password;
 private String name;
 private String lastName;
 private Role role;
 private List<Basket> baskets;

 public UserDTO() {}

 public UserDTO(User user) {
  this.id = user.getId();
  this.lastName = user.getLastName();
  this.name = user.getName();
  this.email = user.getEmail();
  this.password = user.getPassword();
  this.role = user.getRole();
  this.baskets = user.getBaskets();
 }

}
