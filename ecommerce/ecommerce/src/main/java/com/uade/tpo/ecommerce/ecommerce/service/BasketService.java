package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.*;
import com.uade.tpo.ecommerce.ecommerce.repository.BasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductBasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.ProductBasket;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        basketDTO.setBasketId(basket.getId());
        basketDTO.setCreationDate(basket.getCreationDate());
        basketDTO.setUser(basket.getUser());
        basketDTO.setProducts(basket.getProducts());

        return basketDTO;
    }

    private ProductBasketDTO mapToProductBasketDTO(ProductBasket productBasket) {
        ProductBasketDTO productBasketDTO = new ProductBasketDTO();
        productBasketDTO.setProductBasketId(productBasket.getProductBasketId());
        productBasketDTO.setQuantity(productBasket.getQuantity());
        productBasketDTO.setProduct(productBasket.getProduct());
        productBasketDTO.setBasket(productBasket.getBasket());
        return productBasketDTO;
    }

    private Basket createNewBasketForUser(User user) {
        Basket newBasket = new Basket();
        newBasket.setUser(user);
        newBasket.setCreationDate(new Date());
        return basketRepository.save(newBasket);
    }

    public BasketDTO addProductToBasket(String email, Long productId, int quiantity) throws Exception {
        UserDTO user_dto = userService.getUserByEmail(email);
        User user = user_dto.toUser();
        Basket basket = basketRepository.findBasketByUser(user).orElseGet(() -> createNewBasketForUser(user));
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("No se encontro el producto"));
        Optional<ProductBasket> existingProductBasket = productBasketRepository.findByBasketAndProduct(basket, product);

        if (existingProductBasket.isPresent()) {
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

    public void removeProductFromBasket(String email, Long productId) throws Exception {
        UserDTO user_dto = userService.getUserByEmail(email);
        User user = user_dto.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("No se encontro el producto"));
        ProductBasket productBasket = productBasketRepository.findByBasketAndProduct(basket, product)
                .orElseThrow(() -> new Exception("El producto no se encuentra en el carrito"));
        productBasketRepository.delete(productBasket);
    }

    public void clearBasket(String email) throws Exception {
        UserDTO user_dto = userService.getUserByEmail(email);
        User user = user_dto.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));
        List<ProductBasket> productBasketList = productBasketRepository.findByBasket(basket);
        productBasketRepository.deleteAll(productBasketList);

    }

    public Basket getBasketByUserEmail(String email) throws Exception {
        UserDTO user_dto = userService.getUserByEmail(email);
        User user = user_dto.toUser();
        return basketRepository.findBasketByUser(user).orElse(null);
    }

    private BasketSummaryDTO mapToBasketSummaryDTO(Basket basket) {
        BasketSummaryDTO basketSummaryDTO = new BasketSummaryDTO();
        basketSummaryDTO.setCreationDate(basket.getCreationDate());

        // Mapear cada ProductBasket a ProductDTO
        List<ProductInBasketDTO> productDTOs = basket.getProducts().stream()
                .map(productBasket -> new ProductInBasketDTO(
                        productBasket.getProduct().getProductId(),
                        productBasket.getProduct().getName(),
                        productBasket.getProduct().getDescription(),
                        productBasket.getProduct().getPrice(),
                        productBasket.getQuantity()
                        ))
                .collect(Collectors.toList());

        basketSummaryDTO.setProducts(productDTOs);

        return basketSummaryDTO;
    }

    public BasketSummaryDTO getBasketSummaryByUserEmail(String email) throws Exception {
        UserDTO user_dto = userService.getUserByEmail(email);
        User user = user_dto.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));
        return mapToBasketSummaryDTO(basket);
    }

    /*public Double calculateTotal(Long basketId) {
        // Lógica para calcular el total del carrito
    }

    public Checkout checkout(Long userId) {
        // Lógica para manejar el checkout
    }*/
}
