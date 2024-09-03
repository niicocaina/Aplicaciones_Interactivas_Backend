package com.uade.tpo.ecommerce.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
 private Long id;
 private String name;
 private String lastName;
 private String userName;
 private String email;
 private String password;
 private Date birthday;


}
