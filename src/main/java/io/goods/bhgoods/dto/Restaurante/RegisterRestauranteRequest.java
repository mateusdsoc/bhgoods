package io.goods.bhgoods.dto.Restaurante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRestauranteRequest(

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    String email,
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$", 
             message = "Senha deve conter pelo menos uma letra minúscula, uma maiúscula e um caractere especial (@#$%^&+=!)")
    String senha,
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String nome,
    
    String descricao,
    
    @NotBlank(message = "Endereço é obrigatório")
    String endereco,

    @NotBlank(message = "Telefone é obrigatório")
    String telefone
) {}
