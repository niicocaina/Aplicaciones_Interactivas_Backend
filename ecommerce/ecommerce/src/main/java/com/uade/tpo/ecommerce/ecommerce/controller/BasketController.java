package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.*;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.CheckOut;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.ProductBasket;
import com.uade.tpo.ecommerce.ecommerce.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("basket")
@CrossOrigin(origins = "http://localhost:3030")
public class BasketController {

    @Autowired
    private BasketService basketService;

    // Obtener el carrito actual del usuario
    @GetMapping
    public ResponseEntity<BasketSummaryDTO> getBasket() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        BasketSummaryDTO basketSummaryDTO = basketService.getBasketSummaryByUserEmail(email);
        return ResponseEntity.ok(basketSummaryDTO);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProductToBasket(@RequestBody ProductDTO productDTO) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        basketService.addProductToBasket(email, productDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/increase")
    public ResponseEntity<Void> increaseBasket(@RequestBody ProductInBasketDTO productBasketDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Long productBasketId = productBasketDTO.getId();
        basketService.increaseQuantity(email, productBasketId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/decrease")
    public ResponseEntity<Void> decreaseBasket(@RequestBody ProductInBasketDTO productBasketDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Long productBasketId = productBasketDTO.getId();
        basketService.decreaseQuantity(email, productBasketId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeProductFromBasket(@RequestParam Long id) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        basketService.removeProductFromBasket(email, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/removeAll")
    public ResponseEntity<Void> removeAllProductsFromBasket() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        basketService.clearBasket(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<Void> checkout() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        basketService.checkout(email);
        return ResponseEntity.noContent().build();
    }


}
