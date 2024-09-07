package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.UserDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.UserRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("an Error has ocurred"));
        return new UserDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setBirthday(userDTO.getBirthday());
        user.setUserName(userDTO.getUserName());

        User savedUser = userRepository.save(user);

        return new UserDTO(savedUser);
    }
}
