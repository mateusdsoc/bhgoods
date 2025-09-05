package io.goods.bhgoods.dto.Restaurante;

import java.util.List;

import io.goods.bhgoods.dto.Cardapio.CardapioResponse;
import java.util.Set;
import io.goods.bhgoods.enums.StatusAprovacao;
import io.goods.bhgoods.enums.CategoriaRestaurante;

public record ResponseRestaurante(
    Long id,
    String nome,
    String descricao,
    String endereco,
    String telefone,
    CardapioResponse cardapio,
    List<String> fotos,
    Set<CategoriaRestaurante> categorias,
    StatusAprovacao status
) {}
