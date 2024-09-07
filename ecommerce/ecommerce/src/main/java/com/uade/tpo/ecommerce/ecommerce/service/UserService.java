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
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("An error has ocurred"));
        return new UserDTO(user.getId(), user.getName(), user.getEmail(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getRole());
    }
}
