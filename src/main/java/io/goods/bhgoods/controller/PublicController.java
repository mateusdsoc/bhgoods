package io.goods.bhgoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.goods.bhgoods.dto.Restaurante.ResponseRestaurante;
import io.goods.bhgoods.service.RestauranteService;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping("/restaurantes")
    public ResponseEntity<List<ResponseRestaurante>> getRestaurantesPublicos(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "categorias", required = false) List<String> categorias
    ) {
        List<ResponseRestaurante> restaurantes = restauranteService.buscarRestaurantesPublicos(nome, categorias);
        return ResponseEntity.ok(restaurantes);
    }
    
}
