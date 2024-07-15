package br.com.alura.forum_hub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoUsuario(String nome, @Email String login, @Size(min = 8, max = 15) String senha) {
}
