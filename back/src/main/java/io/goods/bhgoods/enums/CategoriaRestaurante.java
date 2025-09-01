package io.goods.bhgoods.enums;

import java.util.List;
import java.util.stream.Collectors;

public enum CategoriaRestaurante {
    SUSHI, 
    CARNES, 
    FRUTOS_DO_MAR, 
    MASSAS, 
    HAMBURGUER, 
    PIZZAS;
    
    // Método para converter lista de strings em lista de enums
    public static List<CategoriaRestaurante> fromStringList(List<String> categoriasStr) {
        if (categoriasStr == null || categoriasStr.isEmpty()) {
            return null;
        }
        return categoriasStr.stream()
            .map(String::toUpperCase)
            .map(CategoriaRestaurante::valueOf)
            .collect(Collectors.toList());
    }
}
