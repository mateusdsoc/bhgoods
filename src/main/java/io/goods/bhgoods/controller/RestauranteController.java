package io.goods.bhgoods.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goods.bhgoods.model.User;

@RestController
@RequestMapping("/api/restaurante")
public class RestauranteController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('RESTAURANTE')")
    public ResponseEntity<String> getRestauranteDashboard(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok("Restaurante Dashboard - Bem-vindo, " + user.getEmail());
    }

    // Manage restaurant menu - Restaurant owner only
    @GetMapping("/cardapio")
    @PreAuthorize("hasRole('RESTAURANTE')")  // Only restaurant owners can manage their menu
    public ResponseEntity<String> getCardapio() {
        // In a real application, this would return the restaurant's actual menu
        return ResponseEntity.ok("Cardápio do restaurante (Restaurant owner only)");
    }
}
