package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.repository.FavoriteRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Favorite;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addToFavorites(Long productId, User user) {
        Favorite favorite = new Favorite();
        favorite.setProductId(productId);
        favorite.setUser(user);
        favoriteRepository.save(favorite);
    }

    public List<Product> getFavoritesForUser(User user) throws Exception {
        // Obtener la lista de favoritos del usuario
        List<Favorite> favorites = favoriteRepository.findByUser(user);

        // Convertir los productIds a objetos Product, manejando posibles errores
        List<Product> favoriteProducts = favorites.stream()
                .map(favorite -> {
                    try {
                        return productRepository.findById(favorite.getProductId())
                                .orElseThrow(() -> new Exception("Producto no encontrado"));
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull) // Filtra los productos nulos (que no se encontraron)
                .collect(Collectors.toList());

        return favoriteProducts;
    }
    @Transactional
    public void removeProductFromFavorites(User user, Long productId) throws Exception{


        Optional<Favorite> favorite = favoriteRepository.findByUserIdAndProductId(user.getId(), productId);

        if (favorite.isPresent()) {
            favoriteRepository.deleteByUserIdAndProductId(user.getId(), productId);
        } else {
            throw new IllegalStateException("El producto no está en la lista de favoritos del usuario.");
        }
    }
}
