package io.goods.bhgoods.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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
        @RequestParam(value = "status", required = false) StatusAprovacao status
    ){
        if(status.equals(StatusAprovacao.APROVADO)){
            return ResponseEntity.ok(
                restauranteService.getRestaurantesByStatus(StatusAprovacao.APROVADO));
        }
        else if(status.equals(StatusAprovacao.PENDENTE)){
            return ResponseEntity.ok(
                restauranteService.getRestaurantesByStatus(StatusAprovacao.PENDENTE));
        }
        else if(status.equals(StatusAprovacao.REJEITADO)){
            return ResponseEntity.ok(
                restauranteService.getRestaurantesByStatus(StatusAprovacao.REJEITADO));
        }
        return ResponseEntity.ok(
            restauranteService.getAllRestaurantes());
    } 

   
    
}
