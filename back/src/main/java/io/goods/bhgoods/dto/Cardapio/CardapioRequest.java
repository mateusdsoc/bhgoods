package io.goods.bhgoods.dto.Cardapio;

import java.util.List;

public record CardapioRequest(List<ItemCardapioRequest> itens) {
	public record ItemCardapioRequest(String nome, Double preco, String descricao) {}
}
