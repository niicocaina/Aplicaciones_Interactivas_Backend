package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.ContactFormDTO;
import com.uade.tpo.ecommerce.ecommerce.service.ContactFormService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping
    public ResponseEntity<String> submitContactForm(@RequestParam("fullName") String fullName,
                                                    @RequestParam("problem") String problem,
                                                    @RequestParam("description") String description,
                                                    @RequestParam(value = "photos", required = false) MultipartFile[] photos) {

        // validacion
        List<String> validationErrors = new ArrayList<>();

        if (fullName == null || fullName.trim().isEmpty()) {
            validationErrors.add("El nombre y apellido es obligatorio.");
        } else if (fullName.length() < 3) {
            validationErrors.add("El nombre y apellido debe tener al menos 3 caracteres.");
        }

        if (problem == null || problem.trim().isEmpty()) {
            validationErrors.add("La problemática es obligatoria.");
        }

        if (description == null || description.trim().isEmpty()) {
            validationErrors.add("La descripción del problema es obligatoria.");
        } else if (description.length() < 10) {
            validationErrors.add("La descripción debe tener al menos 10 caracteres.");
        }


        if (!validationErrors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errores de validación: " + String.join(" ", validationErrors));
        }

        ContactFormDTO contactFormDTO = new ContactFormDTO();
        contactFormDTO.setFullName(fullName);
        contactFormDTO.setProblem(problem);
        contactFormDTO.setDescription(description);
        contactFormDTO.setPhotos(photos);

        try {
            contactFormService.processContactForm(contactFormDTO);
            return ResponseEntity.ok("El problema ha sido registrado correctamente.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar las fotos: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado. Intente nuevamente.");
        }
    }
}
