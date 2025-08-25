package io.goods.bhgoods.model;

import java.util.List;

import io.goods.bhgoods.enums.StatusAprovacao;
import io.goods.bhgoods.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="restaurantes")
public class Restaurante extends User {

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

    public Restaurante() {}
    

    public Restaurante(String email, String senha, String nome, String endereco, String telefone) {
        super(email, senha, UserRole.RESTAURANTE);
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.statusAprovacao = StatusAprovacao.PENDENTE;
    }
    
    // @PrePersist ensures role is always RESTAURANTE before saving to database
    @PrePersist
    private void setRole() {
        super.setRole(UserRole.RESTAURANTE);
    }
    
    public String getNome() { 
        return nome; 
    }
    
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    
    public String getDescricao() { 
        return descricao; 
    }
    
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }
    
    public String getEndereco() { 
        return endereco; 
    }
    
    public void setEndereco(String endereco) { 
        this.endereco = endereco; 
    }
    
    public String getTelefone() { 
        return telefone; 
    }
    
    public void setTelefone(String telefone) { 
        this.telefone = telefone; 
    }
    
    public StatusAprovacao getStatusAprovacao() { 
        return statusAprovacao; 
    }
    
    public void setStatusAprovacao(StatusAprovacao statusAprovacao) { 
        this.statusAprovacao = statusAprovacao; 
    }
    
    public Cardapio getCardapio() { 
        return cardapio; 
    }
    
    public void setCardapio(Cardapio cardapio) { 
        this.cardapio = cardapio; 
    }
    
    public List<FotoRestaurante> getFotos() { 
        return fotos; 
    }
    
    public void setFotos(List<FotoRestaurante> fotos) { 
        this.fotos = fotos; 
    }

}
