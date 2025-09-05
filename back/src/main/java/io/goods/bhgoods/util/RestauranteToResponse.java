package io.goods.bhgoods.util;

import java.util.List;

import io.goods.bhgoods.dto.Restaurante.ResponseRestaurante;
import io.goods.bhgoods.dto.Cardapio.CardapioResponse;
import io.goods.bhgoods.dto.Cardapio.CardapioResponse.ItemCardapioResponse;
import io.goods.bhgoods.model.Cardapio;
import io.goods.bhgoods.model.ItemCardapio;
import io.goods.bhgoods.model.FotoRestaurante;
import io.goods.bhgoods.model.Restaurante;

public class RestauranteToResponse {
    
    public static List<String> conversor(List<FotoRestaurante> fotos) {
        if (fotos == null) return List.of();
        return fotos.stream()
            .map(FotoRestaurante::getUrl)
            .toList();
    }

    public static ResponseRestaurante restauranteToResponse(Restaurante restaurante){
        CardapioResponse cardapioDto = null;
        Cardapio cardapio = restaurante.getCardapio();
        if (cardapio != null) {
            List<ItemCardapioResponse> itens = (cardapio.getItens() == null) ? List.of() :
                cardapio.getItens().stream()
                    .map(RestauranteToResponse::mapItem)
                    .toList();
            cardapioDto = new CardapioResponse(cardapio.getId(), itens);
        }
        return new ResponseRestaurante(
            restaurante.getId(),
            restaurante.getNome(),
            restaurante.getDescricao(),
            restaurante.getEndereco(),
            restaurante.getTelefone(),
            cardapioDto,
            conversor(restaurante.getFotos()),
            restaurante.getCategorias(),
            restaurante.getStatusAprovacao()
        );
    }

    private static ItemCardapioResponse mapItem(ItemCardapio item) {
        return new ItemCardapioResponse(item.getId(), item.getNome(), item.getPreco(), item.getDescricao());
    }
}
