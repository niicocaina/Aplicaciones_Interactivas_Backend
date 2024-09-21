package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.UserDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.UserRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(Long id) throws Exception {

        User user = userRepository.findById(id).orElseThrow(() -> new Exception("an Error has ocurred"));
        return new UserDTO(user.getId(),user.getFirstName(),user.getEmail(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getRole());
    }

    public UserDTO getUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("an Error has ocurred"));
        return new UserDTO(user.getId(),user.getFirstName(),user.getEmail(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getRole());
    }

    //segun entiendo este metodo no sirve mas por que se maneja en el AuthenticationService
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        User savedUser = userRepository.save(user);

        return userDTO;
    }
}
