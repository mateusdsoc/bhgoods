package io.goods.bhgoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.goods.bhgoods.enums.StatusAprovacao;
import io.goods.bhgoods.model.Restaurante;
import io.goods.bhgoods.model.User;
import io.goods.bhgoods.service.RestauranteService;



@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    RestauranteService restauranteService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAdminDashboard(Authentication authentication) {
        // Authentication object contains the authenticated user
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok("Admin Dashboard - Bem-vindo, " + user.getEmail());
    }

    @GetMapping("/restaurantes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Restaurante>> getRestaurantesAprovados(
        @RequestParam(value = "status", required = false) StatusAprovacao status,
        @RequestParam(value = "nome", required = false) String nome,
        @RequestParam(value = "categorias", required = false) List<String> categorias
    ){
        return ResponseEntity.ok(restauranteService.buscarRestaurantesAdmin(nome, categorias, status));
    }

    @PutMapping("/restaurantes/{id}/status/{status}")
    public ResponseEntity<Restaurante> alterarStatusRestaurante(
        @PathVariable Long id,
        @PathVariable StatusAprovacao status
    ){
        return ResponseEntity.ok(restauranteService.atualizarStatusRestaurante(id, status));
    }

    

    

   
    
}
