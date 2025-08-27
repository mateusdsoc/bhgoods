package io.goods.bhgoods.util;

import java.util.List;

import io.goods.bhgoods.dto.Restaurante.ResponseRestaurante;
import io.goods.bhgoods.model.FotoRestaurante;
import io.goods.bhgoods.model.Restaurante;

public class RestauranteToResponse {
    
    public static List<String> conversor(List<FotoRestaurante> fotos) {
        return fotos.stream()
                    .map(FotoRestaurante::getUrl)
                    .toList();
    }

    public static ResponseRestaurante restauranteToResponse(Restaurante restaurante){
        ResponseRestaurante response = new ResponseRestaurante(
            restaurante.getNome(), 
            restaurante.getDescricao(), 
            restaurante.getEndereco(), 
            restaurante.getTelefone(), 
            restaurante.getCardapio(),
            conversor(restaurante.getFotos())
            );
        return response;
    }
}
