package com.uade.tpo.ecommerce.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Role;

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
