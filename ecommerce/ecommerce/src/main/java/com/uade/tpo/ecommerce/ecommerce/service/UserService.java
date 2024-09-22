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

    public UserDTO getUserDTOById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("an Error has ocurred"));
        return user.toUserDTO();
    }

    public UserDTO getUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("an Error has ocurred"));
        return user.toUserDTO();
    }

}
