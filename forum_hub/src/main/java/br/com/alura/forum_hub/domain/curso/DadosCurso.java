package br.com.alura.forum_hub.domain.curso;

import br.com.alura.forum_hub.domain.categoria.Categoria;

import java.util.List;
import java.util.UUID;

public record DadosCurso(UUID id, String nome, Categoria categoria, List<Curso> cursos) {
}
