package com.uade.tpo.ecommerce.ecommerce.dto;


import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Role;

import java.util.List;

@Data
public class UserDTO {
 private Long userId;
 private String email;
 private String password;
 private String firstName;
 private String lastName;
 private Role role;
 private List<Basket> baskets;

 public UserDTO() {}

 public UserDTO(User user) {
  this.userId = user.getUserId();
  this.lastName = user.getLastName();
  this.firstName = user.getFirstName();
  this.email = user.getEmail();
  this.password = user.getPassword();
  this.role = user.getRole();
  this.baskets = user.getBaskets();
 }

}
