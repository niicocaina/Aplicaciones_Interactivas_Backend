package com.uade.tpo.ecommerce.ecommerce.controller.auth;
import com.uade.tpo.ecommerce.ecommerce.dto.UpdatePasswordDTO;
import com.uade.tpo.ecommerce.ecommerce.dto.UserDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import com.uade.tpo.ecommerce.ecommerce.service.AuthenticationService;
import com.uade.tpo.ecommerce.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3030")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserService userService;

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) throws Exception {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/user")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<UserDTO> getLoggedUser (Principal principal) {
        String email = principal.getName();
        try {
            UserDTO userDTO = userService.getUserByEmail(email);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update-password")
    @CrossOrigin(origins = "http://localhost:3030")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO, Principal principal) {
        try {
            String email = principal.getName();
            service.updatePassword(email, updatePasswordDTO);
            return ResponseEntity.ok("Contraseña actualizada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}