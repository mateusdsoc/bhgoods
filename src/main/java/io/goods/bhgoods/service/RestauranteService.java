package io.goods.bhgoods.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goods.bhgoods.dto.Restaurante.ResponseRestaurante;
import io.goods.bhgoods.enums.StatusAprovacao;
import io.goods.bhgoods.exceptions.RestauranteNaoEncontradoException;
import io.goods.bhgoods.model.Restaurante;
import io.goods.bhgoods.repository.RestauranteRepository;
import io.goods.bhgoods.util.RestauranteToResponse;
import org.springframework.data.jpa.domain.Specification;
import io.goods.bhgoods.enums.CategoriaRestaurante;

@Service
public class RestauranteService {

    @Autowired
    RestauranteRepository repository;

    // Método privado para aplicar filtros de categoria
    private Specification<Restaurante> aplicarFiltroCategorias(Specification<Restaurante> spec, List<String> categorias) {
        if (categorias != null && !categorias.isEmpty()) {
            List<CategoriaRestaurante> categoriasEnum = CategoriaRestaurante.fromStringList(categorias);
            if (categoriasEnum != null) {
                spec = spec.and((root, query, cb) -> root.join("categorias").in(categoriasEnum));
            }
        }
        return spec;
    }

    //admin - pode filtrar por qualquer status ou nenhum
    public List<Restaurante> buscarRestaurantesAdmin(String nome, List<String> categorias, StatusAprovacao status) {
        Specification<Restaurante> spec = (root, query, cb) -> cb.conjunction(); // Inicia sem filtros

        if (status != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("statusAprovacao"), status)
            );
        }
        if (nome != null && !nome.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%")
            );
        }
        spec = aplicarFiltroCategorias(spec, categorias);

        return repository.findAll(spec);
    }

    //sempre filtra por APROVADO
    public List<ResponseRestaurante> buscarRestaurantesPublicos(String nome, List<String> categorias) {
        Specification<Restaurante> spec = (root, query, cb) ->
            cb.equal(root.get("statusAprovacao"), StatusAprovacao.APROVADO);

        if (nome != null && !nome.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%")
            );
        }
        spec = aplicarFiltroCategorias(spec, categorias);

        return repository.findAll(spec)
            .stream()
            .map(RestauranteToResponse::restauranteToResponse)
            .toList();
    }

    public Restaurante getRestauranteById(Long id){
        return repository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException());
    }

    public Restaurante getRestauranteByEmail(String email){
        return repository.findByEmail(email).orElseThrow(() -> new RestauranteNaoEncontradoException());
    }

    
}
