package br.com.alura.forum_hub.domain.usuario;

import java.util.UUID;

public record DadosListagemUsuarios(UUID id, String nome) {

    public DadosListagemUsuarios(Usuario usuario) {
        this(usuario.getId(), usuario.getNome());


    }



}
