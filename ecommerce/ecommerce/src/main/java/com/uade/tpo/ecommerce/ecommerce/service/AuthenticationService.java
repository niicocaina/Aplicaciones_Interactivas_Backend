package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.controller.auth.AuthenticationResponse;
import com.uade.tpo.ecommerce.ecommerce.controller.auth.AuthenticationRequest;
import com.uade.tpo.ecommerce.ecommerce.controller.auth.RegisterRequest;
import com.uade.tpo.ecommerce.ecommerce.dto.UpdatePasswordDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.UserRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.uade.tpo.ecommerce.ecommerce.controller.config.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        System.out.println(request);
        var user = User.builder()
                .userName(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .birthDate(request.getBirthDate())
                .build();
        System.out.println(user);
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(()-> new Exception("Usuario o contraseña invalidos"));
        // Verificar si la contraseña es correcta
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception("Usuario o contraseña invalidos");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
    public void updatePassword(String email, UpdatePasswordDTO updatePasswordDTO) throws Exception {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!passwordEncoder.matches(updatePasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new Exception("La contraseña actual es incorrecta");
        }
        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        repository.save(user);
    }

}