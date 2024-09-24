package com.uade.tpo.ecommerce.ecommerce.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
    private String currentPassword;
    private String newPassword;
}
