package br.com.alura.forum_hub.domain.topico;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DadosTopicoResolvido(@NotNull UUID respostaId) {
}
