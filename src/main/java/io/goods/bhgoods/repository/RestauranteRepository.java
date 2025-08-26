package io.goods.bhgoods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.goods.bhgoods.model.Restaurante;
import java.util.List;
import java.util.Optional;

import io.goods.bhgoods.enums.CategoriaRestaurante;
import io.goods.bhgoods.enums.StatusAprovacao;




public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{

    Optional <Restaurante> findByEmail(String email);

    Optional<Restaurante> findById(Long id);

    List<Restaurante> findByStatusAprovacao(StatusAprovacao statusAprovacao);

    List<Restaurante> findByCategoriasInAndStatusAprovacao(List<CategoriaRestaurante> categorias, StatusAprovacao statusAprovacao);
    List<Restaurante> findByNomeContainingAndStatusAprovacao(String nome, StatusAprovacao statusAprovacao);

    //List<Restaurante> findByEnderecoAndStatusAprovacao(String endereco, StatusAprovacao statusAprovacao);
    //List<Restaurante> findByEnderecoContainingAndStatusAprovacao(String endereco, StatusAprovacao statusAprovacao);
}
