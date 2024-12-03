package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.CheckOutDTO;
import com.uade.tpo.ecommerce.ecommerce.dto.UserDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import com.uade.tpo.ecommerce.ecommerce.service.CheckOutService;
import com.uade.tpo.ecommerce.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/checkouts")
@CrossOrigin(origins = "http://localhost:3030")
public class CheckOutController {

    @Autowired
    private CheckOutService checkOutService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<CheckOutDTO>> getAllCheckOuts() throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user_final = userService.getUserByEmail(auth.getName());
        User user = user_final.toUser();
        return ResponseEntity.ok(checkOutService.getAllCheckOuts(user.getId()));
    }

    @PostMapping
    public ResponseEntity<CheckOutDTO> createCheckOut(@RequestBody CheckOutDTO checkOutDTO) {
        return ResponseEntity.ok(checkOutService.createCheckOut(checkOutDTO));
    }


}
