package com.uade.tpo.ecommerce.ecommerce.dto;


import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Role;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
 private Long id;
 private String email;
 private String password;
 private String userName;
 private String firstName;
 private String lastName;
 private Role role;

 public User toUser(){
    User user = new User();
    user.setId(this.id);
    user.setLastName(this.lastName);
    user.setUserName(this.userName);
    user.setEmail(this.email);
    user.setPassword(this.password);
    user.setRole(this.role);
    return user;
 }

}
