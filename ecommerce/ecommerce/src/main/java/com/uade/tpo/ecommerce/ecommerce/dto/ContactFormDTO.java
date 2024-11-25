package com.uade.tpo.ecommerce.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContactFormDTO {
    @NotNull (message = "El nombre y apellido.")
    @Size(min = 3, message ="El nombre y apellido debe tener al menos 3 caracteres.")
    private String fullName;

    @NotNull (message="La problemática es obligatoria.")
    private String problem;

    private MultipartFile [] photos;

    @NotNull(message = "La descripción del problema es obligatoria.")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres.")
    private String description;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProblem() {
        return problem;
    }
    public void setProblem(String problem) {
        this.problem = problem;
    }
    public MultipartFile[] getPhotos() {
        return photos;
    }
    public void setPhotos(MultipartFile[] photos) {
        this.photos = photos;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
