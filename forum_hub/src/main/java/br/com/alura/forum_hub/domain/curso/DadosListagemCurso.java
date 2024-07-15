package br.com.alura.forum_hub.domain.curso;

import br.com.alura.forum_hub.domain.categoria.Categoria;

import java.util.UUID;

public record DadosListagemCurso(UUID id, String nome, Categoria categoria) {

    public DadosListagemCurso(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria());


    }


}
