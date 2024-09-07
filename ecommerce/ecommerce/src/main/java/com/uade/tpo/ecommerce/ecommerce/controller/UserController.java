package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.UserDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import com.uade.tpo.ecommerce.ecommerce.repository.UserRepository;
import com.uade.tpo.ecommerce.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) throws Exception{
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

}
