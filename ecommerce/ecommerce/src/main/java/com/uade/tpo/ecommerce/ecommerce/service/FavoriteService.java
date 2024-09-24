package com.uade.tpo.ecommerce.ecommerce.service;

import com.uade.tpo.ecommerce.ecommerce.repository.FavoriteRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.ProductRepository;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Favorite;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.Product;
import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public void addToFavorites(Long productId, User user) {
        Favorite favorite = new Favorite();
        favorite.setProductId(productId);
        favorite.setUser(user);
        favoriteRepository.save(favorite);
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
