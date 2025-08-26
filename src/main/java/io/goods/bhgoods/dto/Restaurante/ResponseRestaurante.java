package io.goods.bhgoods.dto.Restaurante;

import java.util.List;

import io.goods.bhgoods.model.Cardapio;

public record ResponseRestaurante(
    String nome,
    String descricao,
    String endereco,
    String telefone,
    Cardapio cardapio,
    List<String> fotos
) {}
