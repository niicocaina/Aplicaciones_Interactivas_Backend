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
        Product product = productRepository.findById(id).
                orElseThrow(() -> new Exception("No se ha encontrado al usuario"));
        return product.toProductDTO();
    }

    public ProductDTO saveNewProduct(ProductDTO productDTO) throws Exception{
        Product product = productDTO.toProduct();
        productRepository.save(product);
        return productDTO;
    }

    public void deleteProductById(Long id) throws Exception{
        productRepository.deleteById(id);
    }

    public void updateStockById(ProductDTO productDTO) throws Exception{
        // Busca el producto existente por ID
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new Exception("Product not found with id: " + productDTO.getId()));
        // Actualiza solo el campo de stock
        product.setStock(productDTO.getStock());
        // Guarda el producto actualizado
        productRepository.save(product);
    }
}
