package io.goods.bhgoods.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.goods.bhgoods.model.User;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAdminDashboard(Authentication authentication) {
        // Authentication object contains the authenticated user
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok("Admin Dashboard - Bem-vindo, " + user.getEmail());
    }

    @GetMapping("/restaurantes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAllRestaurantes() {
        // In a real application, this would return actual restaurant data
        return ResponseEntity.ok("Lista de todos os restaurantes (Admin only)");
    }
}
