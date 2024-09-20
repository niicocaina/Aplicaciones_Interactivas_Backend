package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.BasketSummaryDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping("/add/")
    public ResponseEntity<Void> addProductToBasket(@RequestBody BasketRequest basketRequest) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        basketService.addProductToBasket(email, basketRequest.getProductId(), basketRequest.getQuantity());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeProductFromBasket(@RequestParam Long productId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        basketService.removeProductFromBasket(email, productId);
        return ResponseEntity.noContent().build();
    }

    // Obtener el carrito actual del usuario
    @GetMapping
    public ResponseEntity<BasketSummaryDTO> getBasket() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        BasketSummaryDTO basketSummaryDTO = basketService.getBasketSummaryByUserEmail(email);
        return ResponseEntity.ok(basketSummaryDTO);
    }


}
