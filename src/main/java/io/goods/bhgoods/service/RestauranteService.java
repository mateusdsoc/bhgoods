package io.goods.bhgoods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goods.bhgoods.enums.StatusAprovacao;
import io.goods.bhgoods.exceptions.RestauranteNaoEncontradoException;
import io.goods.bhgoods.model.Restaurante;
import io.goods.bhgoods.repository.RestauranteRepository;

@Service
public class RestauranteService {

    @Autowired
    RestauranteRepository repository;

    public List<Restaurante> getAllRestaurantes(){
        return repository.findAll();
    }
    public List<Restaurante> getRestaurantesByStatus(StatusAprovacao status){
        return repository.findByStatusAprovacao(status);
    }

    public List<Restaurante> getRestaurantesByNomeContainingAndStatus(String nome, StatusAprovacao status){
        return repository.findByNomeContainingAndStatusAprovacao(nome, status);
    }
  
    public Restaurante getRestauranteById(Long id){
        return repository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException());
    }
    public Restaurante getRestauranteByEmail(String email){
        return repository.findByEmail(email).orElseThrow(() -> new RestauranteNaoEncontradoException());
    }
    
}
