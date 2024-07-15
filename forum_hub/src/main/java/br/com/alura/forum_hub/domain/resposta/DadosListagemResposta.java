package br.com.alura.forum_hub.domain.resposta;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosListagemResposta(UUID id, String autor, String mensagem , String topico, boolean solucao,
                                    LocalDateTime dataCriacao) {


    public DadosListagemResposta(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getUsuario().getNome(),
                resposta.getMensagem(),
                resposta.getTopico().getTitulo(),
                resposta.getSolucao(),
                resposta.getDataCriacao());



    }





}
