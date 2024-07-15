package br.com.alura.forum_hub.domain.usuario;

import br.com.alura.forum_hub.domain.topico.Topico;

import java.util.List;
import java.util.UUID;

public record DadosUsuario(UUID id, String nome, String email, String senha, List<Topico> topicos) {
}
