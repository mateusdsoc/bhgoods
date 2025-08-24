package io.goods.bhgoods.model;

import java.util.List;

import io.goods.bhgoods.enums.StatusAprovacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="restaurante")
public class Restaurante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 300)
    private String descricao;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private StatusAprovacao statusAprovacao;

    @OneToOne(mappedBy = "restaurante")
    private Cardapio cardapio;

    @OneToMany(mappedBy = "restaurante")
    private List<FotoRestaurante> fotos;


}
