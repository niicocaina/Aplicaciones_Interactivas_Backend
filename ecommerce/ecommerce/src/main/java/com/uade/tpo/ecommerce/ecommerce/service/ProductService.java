package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.UserRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductDTO getProductById(Long id) throws Exception{
        Product product = productRepository.findById(id).orElseThrow(() -> new Exception("an Error has ocurred"));
        return new ProductDTO(product.getProductId(),product.getName(), product.getDescription(), product.getPrice(),
                product.getPromotionalPrice(), product.getStock(), product.getCategory());
    }
}
