package io.goods.bhgoods.dto.Cardapio;

import java.util.List;

public record CardapioResponse(Long id, List<ItemCardapioResponse> itens) {
	public record ItemCardapioResponse(Long id, String nome, Double preco, String descricao) {}
}
