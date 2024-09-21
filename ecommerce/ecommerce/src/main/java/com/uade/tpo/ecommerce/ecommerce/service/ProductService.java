package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductDTO getProductById(Long id) throws Exception{
        Product product = productRepository.findById(id).
                orElseThrow(() -> new Exception("No se ha encontrado el producto"));
        return product.toProductDTO();
    }

    public void saveNewProduct(ProductDTO productDTO) throws Exception{
        try {
            Product product = productDTO.toProduct();
            productRepository.save(product);
        }catch(Exception e){
            throw new Exception("No se ha podido guardar el producto");
        }
    }

    public void deleteProductById(Long id) throws Exception{
        try {
            productRepository.deleteById(id);
        }catch (Exception e){
            throw new Exception("No se ha podido eliminar el producto");
        }
    }

    public void updateStockById(Long id, Integer newStock) throws Exception{
        // Busca el producto existente por ID
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Producto no encontrado con id: "+id));
        // Actualiza solo el campo de stock
        product.setStock(newStock);
        // Guarda el producto actualizado
        try {
            productRepository.save(product);
        }catch (Exception e){
            throw new Exception("No se ha podido actualizar el stock del producto");
        }
    }
}
