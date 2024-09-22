package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.dto.ProductDTO;
import com.uade.tpo.ecommerce.ecommerce.repository.BasketRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.FavoriteRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.RecentlyViewedRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.*;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private RecentlyViewedRepository recentlyViewedRepository;

    @Autowired
    private BasketRepository basketRepository;

    public List<Product> getFeaturedProducts() {
        return productRepository.findFeaturedProducts();
    public ProductDTO getProductById(Long id) throws Exception{
        Product product = productRepository.findById(id).
                orElseThrow(() -> new Exception("No se ha encontrado el producto"));
        return product.toProductDTO();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getRecentlyViewedProducts(User user) {
        return productRepository.findRecentlyViewedByUser(user.getId());
    public void saveNewProduct(ProductDTO productDTO) throws Exception{
        try {
            Product product = productDTO.toProduct();
            productRepository.save(product);
        }catch(Exception e){
            throw new Exception("No se ha podido guardar el producto");
        }
    }

    public ProductDTO getProductDetail(Long productId,User user) throws Exception {

        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Producto no encontrado"));
        RecentlyViewed recent = new RecentlyViewed();
        recent.setProduct(product);
        recent.setUser(user);
        recent.setViewedAt(LocalDateTime.now());
        recentlyViewedRepository.save(recent);
        return new ProductDTO(product);
    }

    public void addToFavorites(Long productId, User user) {
        Favorite favorite = new Favorite();
        favorite.setProductId(productId);
        favorite.setUser(user);
        favoriteRepository.save(favorite);
    public void deleteProductById(Long id) throws Exception{
        try {
            productRepository.deleteById(id);
        }catch (Exception e){
            throw new Exception("No se ha podido eliminar el producto");
        }
    }

    public boolean addToBasket(Long productId, User user) throws Exception{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("PRoducto no encontrado"));

        if (product.getStock() > 0) {
            Basket basket = new Basket();
            basket.setUser(user);
            basket.setProductId(productId);
            basketRepository.save(basket);
            return true;
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
        return false;
    }
}
