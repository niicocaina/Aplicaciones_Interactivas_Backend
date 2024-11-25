package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.ContactFormDTO;
import com.uade.tpo.ecommerce.ecommerce.service.ContactFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api/v1/contact")
@Validated

public class ContactFormController {

    @Autowired
    private ContactFormService contactFormService;

    @PostMapping
    public ResponseEntity <String> submitContactForm(@ModelAttribute ContactFormDTO contactFormDTO) {
        try {
            contactFormService.processContactForm(contactFormDTO);
            return ResponseEntity.ok("El problema ha sido registrado correctamente.");
        } catch (Exception e){
            return ResponseEntity.status(500).body("Hubo un error al registrar el problema. Intenta nuevamente.");
        }
    }

}
