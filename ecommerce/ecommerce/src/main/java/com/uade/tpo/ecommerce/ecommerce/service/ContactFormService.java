package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.ContactFormDTO;
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
                    uploadDirFile.mkdirs();
                }

                String filePath = uploadDir + File.separator + photo.getOriginalFilename();
                File file = new File(filePath);
                if (file.exists()) {
                    throw new IOException("El archivo ya existe: " + photo.getOriginalFilename());
                }

                Files.copy(photo.getInputStream(), Paths.get(filePath));
            } catch (IOException e) {
                throw new IOException("No se pudo guardar la imagen: " + e.getMessage(), e);
            }
        }
    }

    private void saveFormData(ContactFormDTO contactFormDTO) {
        List<String> filePaths = new ArrayList<>();  // la lista es para guardar las rutas de las fotos

        if (contactFormDTO.getPhotos() != null) {
            for (MultipartFile file : contactFormDTO.getPhotos()) {
                String filePath = file.getOriginalFilename();
                filePaths.add(filePath);
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
