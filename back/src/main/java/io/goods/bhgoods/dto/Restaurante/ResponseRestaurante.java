package io.goods.bhgoods.dto.Restaurante;

import java.util.List;

import io.goods.bhgoods.dto.Cardapio.CardapioResponse;

public record ResponseRestaurante(
    String nome,
    String descricao,
    String endereco,
    String telefone,
    CardapioResponse cardapio,
    List<String> fotos
) {}
