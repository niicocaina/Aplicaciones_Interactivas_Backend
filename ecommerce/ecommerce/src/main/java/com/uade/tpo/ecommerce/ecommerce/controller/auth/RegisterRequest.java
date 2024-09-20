package com.uade.tpo.ecommerce.ecommerce.controller.auth;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
