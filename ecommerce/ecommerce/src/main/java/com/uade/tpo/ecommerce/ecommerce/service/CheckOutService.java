package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.CheckOutDTO;
import com.uade.tpo.ecommerce.ecommerce.dto.UserDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.CheckOutRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.CheckOut;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckOutService {
    UserService userService;

    @Autowired
    private CheckOutRepository checkOutRepository;

    public List<CheckOutDTO> getAllCheckOuts(Long userid) {
        List<CheckOut> checkOuts = checkOutRepository.findByUserId(userid);
        return checkOuts.stream()
                .map(CheckOut::toDTO)
                .collect(Collectors.toList());
    }

    public CheckOutDTO createCheckOut(CheckOutDTO checkOutDTO) {
        CheckOut checkOut = new CheckOut();
        checkOut.setTransactionDate(checkOutDTO.getTransactionDate());
        checkOut.setTotal(checkOutDTO.getTotal());
        checkOut.setUser(checkOutDTO.getUser());
        checkOut.setProducts(checkOutDTO.getProducts());
        checkOut = checkOutRepository.save(checkOut);
        return checkOut.toDTO();
    }
}
