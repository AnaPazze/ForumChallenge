package br.com.alura.forum_hub.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DadosCadastroResposta(@NotBlank String mensagem, @NotNull UUID topicoId) {




}

