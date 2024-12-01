package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.*;
import com.uade.tpo.ecommerce.ecommerce.repository.BasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.CheckOutRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductBasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.*;
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
    @Autowired
    private CheckOutRepository checkOutRepository;

    private BasketDTO mapToBasketDTO(Basket basket) throws Exception {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setBasketId(basket.getId());
        basketDTO.setCreationDate(basket.getCreationDate());
        basketDTO.setUser(basket.getUser());
        basketDTO.setProducts(basket.getProducts());

        return basketDTO;
    }

    private Basket createNewBasketForUser(User user) {
        Basket newBasket = new Basket();
        newBasket.setUser(user);
        newBasket.setCreationDate(new Date());
        return basketRepository.save(newBasket);
    }

    public void addProductToBasket(String email, Product product, ProductBasket productBasket) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user).orElseGet(() -> createNewBasketForUser(user));
        long productId = product.getProductId();
        Product productToAdd = productRepository.findById(productId).orElseThrow(() -> new Exception("No se encontro el producto"));
        Optional<ProductBasket> existingProductBasket = productBasketRepository.findById(productBasket.getProductBasketId());

        if (existingProductBasket.isPresent()) {
            ProductBasket finalProductBasket = existingProductBasket.get();
            productBasket.setQuantity(finalProductBasket.getQuantity() + 1);
            productBasketRepository.save(productBasket);
        } else {
            ProductBasket newProductBasket = new ProductBasket();
            newProductBasket.setBasket(basket);
            newProductBasket.setProduct(productToAdd);
            newProductBasket.setQuantity(1);
            productBasketRepository.save(newProductBasket);
        }
    }

    public void removeProductFromBasket(String email, Long productBasketId) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));
        for (ProductBasket productBasket : basket.getProducts()) {
            if (productBasket.getProductBasketId().equals(productBasketId)) {
                productBasketRepository.delete(productBasket);
            }
        }
    }

    public void clearBasket(String email) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));
        List<ProductBasket> productBasketList = productBasketRepository.findByBasket(basket);
        productBasketRepository.deleteAll(productBasketList);

    }

    public void increaseQuantity(String email, Long productBasketId) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));
        for (ProductBasket productBasket : basket.getProducts()) {
            if (productBasket.getProductBasketId().equals(productBasketId)) {
                productBasket.setQuantity(productBasket.getQuantity() + 1);
            }
        }
    }

    public void decreaseQuantity(String email, Long productBasketId) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));
        for (ProductBasket productBasket : basket.getProducts()) {
            if (productBasket.getProductBasketId().equals(productBasketId)) {
                if (productBasket.getQuantity() - 1 == 0) {
                    removeProductFromBasket(email, productBasket.getProductBasketId());
                }
                else {
                    productBasket.setQuantity(productBasket.getQuantity() - 1);
                }
            }
        }
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
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));
        return mapToBasketSummaryDTO(basket);
    }

    public Double calculateTotal(String email) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));
        double finalPrice = 0;
        for (ProductBasket productBasket : basket.getProducts()){
            finalPrice += productBasket.getProduct().getPrice();
        }
        return finalPrice;
    }

    public CheckOut checkout(String email) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));

        for (ProductBasket productBasket : basket.getProducts()) {
            Product product = productBasket.getProduct();

            if (product.getStock() < productBasket.getQuantity()) {
                throw new Exception("No hay suficiente stock para el producto: " + product.getName());
            }
            else{
                product.setStock(product.getStock() - productBasket.getQuantity());
                productRepository.save(product);
            }
        }

        double totalPrice = calculateTotal(email);

        List<ProductBasket> copiedProducts = basket.getProducts().stream()
                .map(pb -> {
                    ProductBasket newProductBasket = new ProductBasket();
                    newProductBasket.setProduct(pb.getProduct());
                    newProductBasket.setQuantity(pb.getQuantity());
                    return productBasketRepository.save(newProductBasket);
                }).collect(Collectors.toList());

        CheckOut checkOut = new CheckOut();
        checkOut.setTotal(totalPrice);
        checkOut.setTransactionDate(new Date());
        checkOut.setUser(user);
        checkOut.setProducts(copiedProducts);

        checkOutRepository.save(checkOut);
        clearBasket(email);

        return checkOut;
    }
}
