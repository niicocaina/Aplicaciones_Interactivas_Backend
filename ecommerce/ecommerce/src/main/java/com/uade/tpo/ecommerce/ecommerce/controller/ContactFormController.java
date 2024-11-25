package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.ContactFormDTO;
import com.uade.tpo.ecommerce.ecommerce.service.ContactFormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/contact")
@Validated
public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping
    public ResponseEntity<String> submitContactForm(@ModelAttribute @Valid ContactFormDTO contactFormDTO, BindingResult bindingResult) { // Cambié de javax a jakarta
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.append(error.getDefaultMessage()).append(" ");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errores de validación: " + errorMessages.toString());
        }

        try {
            contactFormService.processContactForm(contactFormDTO);
            return ResponseEntity.ok("El problema ha sido registrado correctamente.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Hubo un error inesperado. Intenta nuevamente.");
        }
    }
}
