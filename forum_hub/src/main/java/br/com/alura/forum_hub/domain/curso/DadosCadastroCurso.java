package br.com.alura.forum_hub.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCurso(@NotBlank String nome, @NotBlank String categoria) {
}
