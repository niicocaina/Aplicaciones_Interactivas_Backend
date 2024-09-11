package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.BasketDTO;
import com.uade.tpo.ecommerce.ecommerce.dto.ProductBasketDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.BasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductBasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.ProductBasket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BasketService {
    @Autowired
    private BasketRepository basketRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductBasketRepository productBasketRepository;
    @Autowired
    private UserService userService;

    private BasketDTO mapToBasketDTO(Basket basket) throws Exception {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBasketId(basket.getBasketId());
        basketDTO.setCreationDate(basket.getCreationDate());
        basketDTO.setUser(userService.getUserDTOById(basket.getUser().getUserId()));

        List<ProductBasketDTO> productBasketDTOs = basket.getProducts().stream()
                .map(this::mapToProductBasketDTO)
                .collect(Collectors.toList());

        basketDTO.setProducts(productBasketDTOs);

        return basketDTO;
    }

    private ProductBasketDTO mapToProductBasketDTO(ProductBasket productBasket) {
        ProductBasketDTO productBasketDTO = new ProductBasketDTO();
        return productBasketDTO;
    }

    private Basket createNewBasketForUser(User user) {
        Basket newBasket = new Basket();
        newBasket.setUser(user);
        newBasket.setCreationDate(new Date());
        return basketRepository.save(newBasket);
    }

    public BasketDTO addProductToBasket(Long userId, Long productId, int quiantity) throws Exception {
        User user = userService.getUserById(userId);
        Basket basket = basketRepository.findBasketByUser(user).orElseGet(() -> createNewBasketForUser(user));
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("No se encontro el producto"));
        Optional<ProductBasket> existingProductBasket = productBasketRepository.findByBasketAndProduct(basket, product);

        if(existingProductBasket.isPresent()) {
            ProductBasket productBasket = existingProductBasket.get();
            productBasket.setQuantity(productBasket.getQuantity() + quiantity);
            productBasketRepository.save(productBasket);
        } else {
            ProductBasket newProductBasket = new ProductBasket();
            newProductBasket.setBasket(basket);
            newProductBasket.setProduct(product);
            newProductBasket.setQuantity(quiantity);
            productBasketRepository.save(newProductBasket);
        }

        basket = basketRepository.save(basket);
        return mapToBasketDTO(basket);
    }

}
