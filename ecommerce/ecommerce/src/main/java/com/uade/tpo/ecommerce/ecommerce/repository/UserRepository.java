package com.uade.tpo.ecommerce.ecommerce.repository;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.User;
import com.uade.tpo.ecommerce.ecommerce.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

}
