package br.com.alura.forum_hub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DadosAtualizacaoTopico(@NotNull UUID id, @NotBlank String titulo, @NotBlank String mensagem) {
}
