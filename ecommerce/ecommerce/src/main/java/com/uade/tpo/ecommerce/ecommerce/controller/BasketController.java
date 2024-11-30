package com.uade.tpo.ecommerce.ecommerce.controller;

import com.uade.tpo.ecommerce.ecommerce.dto.BasketSummaryDTO;
import com.uade.tpo.ecommerce.ecommerce.dto.CheckOutDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.CheckOut;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.ProductBasket;
import com.uade.tpo.ecommerce.ecommerce.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping("/add")
    public ResponseEntity<Void> addProductToBasket(@RequestBody Product product, ProductBasket productBasket) throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        basketService.addProductToBasket(email, product, productBasket);
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

    @PutMapping("/increase")
    public ResponseEntity<Void> increaseBasket(@RequestBody Long productBasketId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        basketService.increaseQuantity(email, productBasketId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<CheckOutDTO> checkout() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        CheckOut checkout = basketService.checkout(email);
        return ResponseEntity.ok(checkout.toDTO());
    }


}
