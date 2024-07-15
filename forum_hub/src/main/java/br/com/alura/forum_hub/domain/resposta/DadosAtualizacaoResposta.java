package br.com.alura.forum_hub.domain.resposta;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoResposta(@NotBlank String mensagem) {



    public DadosAtualizacaoResposta(Resposta resposta){
        this(resposta.getMensagem());
    }

}
