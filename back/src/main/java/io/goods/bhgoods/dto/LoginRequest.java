package io.goods.bhgoods.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    
    @NotBlank(message = "Email é obrigatório")           
    @Email(message = "Email deve ter um formato válido")
    String email,
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$", 
             message = "Senha deve conter pelo menos uma letra minúscula, uma maiúscula e um caractere especial (@#$%^&+=!)")
    String senha
) {}
