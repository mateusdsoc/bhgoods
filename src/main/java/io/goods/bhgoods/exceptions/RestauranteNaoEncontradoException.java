package io.goods.bhgoods.exceptions;

public class RestauranteNaoEncontradoException extends RuntimeException{
    public RestauranteNaoEncontradoException(){
        super("Restaurante não encontrado");
    }
}
