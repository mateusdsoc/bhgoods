package io.goods.bhgoods.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.goods.bhgoods.model.Restaurante;
import java.util.List;
import java.util.Optional;
import io.goods.bhgoods.enums.StatusAprovacao;




public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, JpaSpecificationExecutor<Restaurante> {

    Optional <Restaurante> findByEmail(String email);

    Optional<Restaurante> findById(Long id);

    List<Restaurante> findByStatusAprovacao(StatusAprovacao statusAprovacao);


}
