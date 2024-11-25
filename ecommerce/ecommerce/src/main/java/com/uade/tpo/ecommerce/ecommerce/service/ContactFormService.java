package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.ContactFormDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.BasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ContactFormRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactFormService {
    @Autowired
    private ContactFormRepository contactFormRepository;

    public void processContactForm(ContactFormDTO contactFormDTO) throws IOException {

        if (contactFormDTO.getPhotos() != null) {
            for (MultipartFile file : contactFormDTO.getPhotos()) {
                saveImage(file);
            }
        }

        saveFormData(contactFormDTO);
    }

    private void saveImage(MultipartFile photo) throws IOException {
        if (!photo.isEmpty()) {
            try {
                String uploadDir = "uploads/contact-photos";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();  // Crear directorio si no existe
                }
                String filePath = uploadDir + File.separator + photo.getOriginalFilename();
                Files.copy(photo.getInputStream(), Paths.get(filePath));  // Guardar el archivo
            } catch (IOException e) {
                System.out.println("Error al guardar la imagen: " + e.getMessage());
                throw new IOException("Error al guardar la imagen", e);
            }
        }
    }


    private void saveFormData(ContactFormDTO contactFormDTO) {
        System.out.println("Formulario recibido: ");
        System.out.println("Nombre y apellido: " + contactFormDTO.getFullName());
        System.out.println("Problemática: " + contactFormDTO.getProblem());
        System.out.println("Descripción: " + contactFormDTO.getDescription());

        List<String> filePaths = new ArrayList<>();  // Lista para almacenar las rutas de los archivos

        if (contactFormDTO.getPhotos() != null) {
            for (MultipartFile file : contactFormDTO.getPhotos()) {
                String filePath = file.getOriginalFilename(); // Llamamos al método para guardar la imagen y obtener la ruta
                filePaths.add(filePath); // Agregamos la ruta del archivo a la lista
            }
        }
        ContactForm contactForm = new ContactForm();
        contactForm.setFullName(contactFormDTO.getFullName());
        contactForm.setProblem(contactFormDTO.getProblem());
        contactForm.setDescription(contactFormDTO.getDescription());
        contactForm.setPhotoPaths(String.join(";", filePaths));
        contactFormRepository.save(contactForm);
    }

    }
