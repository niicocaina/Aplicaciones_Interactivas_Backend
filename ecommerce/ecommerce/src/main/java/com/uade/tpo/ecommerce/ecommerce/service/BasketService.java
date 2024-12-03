package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.*;
import com.uade.tpo.ecommerce.ecommerce.repository.BasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.CheckOutRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductBasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.*;
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
    @Autowired
    private CheckOutRepository checkOutRepository;

    private Basket createNewBasketForUser(User user) {
        Basket newBasket = new Basket();
        newBasket.setUser(user);
        newBasket.setCreationDate(new Date());
        return basketRepository.save(newBasket);
    }

    public void addProductToBasket(String email, ProductDTO productDTO) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user).orElseGet(() -> createNewBasketForUser(user));
        long productId = productDTO.getId();
        Product productToAdd = productRepository.findById(productId).orElseThrow(() -> new Exception("No se encontro el producto"));
        try {
            if (basket.getProducts() == null) {
                basket.setProducts(new ArrayList<>());  // Inicializa la lista de productos vacía
            }
            if (basket.getProducts().isEmpty()) {
                ProductBasket newProductBasket = new ProductBasket();
                newProductBasket.setBasket(basket);
                System.out.println("Basket agregado al newProductBasket: " + newProductBasket.getBasket());
                newProductBasket.setProduct(productToAdd);
                newProductBasket.setQuantity(1);
                basket.getProducts().add(newProductBasket);
                productBasketRepository.save(newProductBasket);
                basketRepository.save(basket);
                System.out.println("ProductBasket agregado");
            }
            else {
                boolean productExists = false;
                for (ProductBasket productBasket : basket.getProducts()) {
                    Product producto = productBasket.getProduct();
                    System.out.println("Dentro del for");
                    if (producto.getProductId() == productId) {
                        productBasket.setQuantity(productBasket.getQuantity() + 1);
                        productBasketRepository.save(productBasket);
                        System.out.println("Se aumento la cantidad");
                        productExists = true;
                        break;
                    }
                }
                if (!productExists) {
                    ProductBasket newProductBasket = new ProductBasket();
                    newProductBasket.setBasket(basket);
                    newProductBasket.setProduct(productToAdd);
                    newProductBasket.setQuantity(1);
                    basket.getProducts().add(newProductBasket);
                    productBasketRepository.save(newProductBasket);
                    basketRepository.save(basket);
                    System.out.println("Producto agregado al carrito como un nuevo ProductBasket");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clearBasket(String email) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));
        productBasketRepository.deleteAll(basket.getProducts());

    }

    public void increaseQuantity(String email, Long productBasketId) throws Exception {
        UserDTO userDTO = userService.getUserByEmail(email);
        User user = userDTO.toUser();
        Basket basket = basketRepository.findBasketByUser(user)
                .orElseThrow(() -> new Exception("No existe el carrito"));
        System.out.println("Id productBasket: " + productBasketId);
        for (ProductBasket productBasket : basket.getProducts()) {
            if (productBasket.getProductBasketId().equals(productBasketId)) {
                productBasket.setQuantity(productBasket.getQuantity() + 1);
                productBasketRepository.save(productBasket);
            }
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
                    productBasketRepository.save(productBasket);
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
                        productBasket.getProductBasketId(),
                        productBasket.getProduct().getProductId(),
                        productBasket.getProduct().getName(),
                        productBasket.getProduct().getDescription(),
                        productBasket.getProduct().getPrice(),
                        productBasket.getQuantity(),
                        productBasket.getProduct().getImg1()
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

    public void checkout(String email) throws Exception {
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
    }
}
