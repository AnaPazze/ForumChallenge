package br.com.alura.forum_hub.domain.topico;

import br.com.alura.forum_hub.domain.curso.Curso;
import jakarta.transaction.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosListagemTopico(UUID id,String titulo,
                                  String mensagem,
                                  LocalDateTime dataCriacao, Status status,
                                  String autor, String curso) {


    public DadosListagemTopico(Topico topico){
        this(topico.getId(),topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getStatus(),topico.getUsuario().getNome(),
                topico.getCurso().getNome());





    }


}
