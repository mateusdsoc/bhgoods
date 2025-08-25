package io.goods.bhgoods.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.goods.bhgoods.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    // Useful for registration validation
    boolean existsByEmail(String email);
}
