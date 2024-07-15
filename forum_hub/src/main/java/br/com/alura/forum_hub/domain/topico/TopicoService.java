package br.com.alura.forum_hub.domain.topico;

import br.com.alura.forum_hub.Controller.TopicoController;
import br.com.alura.forum_hub.domain.curso.Curso;
import br.com.alura.forum_hub.domain.curso.CursoRepository;
import br.com.alura.forum_hub.domain.resposta.Resposta;
import br.com.alura.forum_hub.domain.resposta.RespostaRepository;
import br.com.alura.forum_hub.domain.status.DadosErros;
import br.com.alura.forum_hub.domain.status.DadosSucesso;
import br.com.alura.forum_hub.domain.status.Status;
import br.com.alura.forum_hub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TopicoService {

        @Autowired
        private TopicoRepository topicoRepository;
        @Autowired
        private CursoRepository cursoRepository;
        @Autowired
        RespostaRepository respostaRepository;

        public ResponseEntity<?> cadastrarTopico(DadosCadastroTopico dadosCadastroTopicos, UriComponentsBuilder uriBuilder) {
            Curso curso = cursoRepository.findByNome(dadosCadastroTopicos.curso());

            if (curso == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Curso não encontrado!"));
            }

            Topico topico = new Topico(dadosCadastroTopicos.titulo(), dadosCadastroTopicos.mensagem(), curso);
            topicoRepository.save(topico);

            topico.add(linkTo(methodOn(TopicoController.class).listarTodosTopicos(Pageable.unpaged())).withRel("Lista de tópicos"));
            URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosListagemTopico(topico));
        }

        public ResponseEntity<Page<DadosListagemTopico>> listarTodosTopicos(Pageable paginacao) {

            Page<DadosListagemTopico> page = topicoRepository.findAll(paginacao)
                    .map(t -> {
                        t.add(linkTo(methodOn(TopicoController.class).listarTopico(t.getId())).withRel("Detalhes do Tópico"));
                        return new DadosListagemTopico(
                                t.getId(),
                                t.getTitulo(),
                                t.getMensagem(),
                                t.getDataCriacao(),
                                t.getStatus(),
                                t.getUsuario().getNome(),
                                t.getCurso().getNome());

                    });

            return ResponseEntity.ok(page);
        }

        public ResponseEntity<?> listarTopico(UUID id) {
            Optional<Topico> optionalTopico = topicoRepository.findById(id);
            if (optionalTopico.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Tópico não encontrado!"));
            }
            Topico topico = optionalTopico.get();
            topico.add(linkTo(methodOn(TopicoController.class).listarTodosTopicos(Pageable.unpaged())).withRel("Lista de Tópicos"));
            return ResponseEntity.ok(new DadosListagemTopico(topico));

        }

        public ResponseEntity<?> atualizarTopico(DadosAtualizacaoTopico dadosAtualizacaoTopico, UUID id) {

            Optional<Topico> optionalTopico = topicoRepository.findById(id);

            if (optionalTopico.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Tópico não existe!"));
            }
            Topico topico = optionalTopico.get();
            Usuario usuario = topico.getUsuario();

            if (!Usuario.temPermisaoParaModificacao(usuario)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DadosErros("Você não tem permissão para modificar tópicos de outra pessoa!"));
            }

            topico.atualizarInformacoes(dadosAtualizacaoTopico);
            topico.add(linkTo(methodOn(TopicoController.class).listarTodosTopicos(Pageable.unpaged())).withRel("Lista de tópicos"));

            return ResponseEntity.ok(new DadosListagemTopico(topico));
        }

        public ResponseEntity<?> excluirTopico(UUID id) {
            Optional<Topico> optionalTopico = topicoRepository.findById(id);

            if (optionalTopico.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Tópico não encontrado para a exclusão!"));
            }

            Usuario usuario = optionalTopico.get().getUsuario();
            if (!Usuario.temPermisaoParaModificacao(usuario)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DadosErros("Você não tem permissão para excluir tópicos de outra pessoa!"));
            }

            topicoRepository.deleteById(id);

            return ResponseEntity.ok(new DadosSucesso("Tópico excluído com sucesso!"));
        }

        public ResponseEntity<?> atualizarStatusTopico(UUID id, DadosTopicoResolvido dadosTopicoResolvido) {
            Optional<Topico> optionalTopico = topicoRepository.findById(id);
            Optional<Resposta> optionalResposta = respostaRepository.findById(dadosTopicoResolvido.respostaId());
            if (optionalTopico.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Tópico não encontrado!"));
            }
            if (optionalResposta.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Resposta não encontrada neste tópico!"));
            }

            Topico topico = optionalTopico.get();
            Resposta resposta = optionalResposta.get();

            if(!Objects.equals(resposta.getTopico().getId(), topico.getId())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErros("Esta resposta não pertence a esse tópico!"));
            }

            if(!Usuario.temPermisaoParaModificacao(topico.getUsuario())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErros("Você não tem permissão de alterar o status do tópico de outra pessoa!"));
            }
            List<Resposta> respostas = respostaRepository.buscarRespostaPorSolucaoPorTopico(true,topico);
            respostas.forEach(r -> r.setSolucao(false));

            topico.setStatus(Status.NAO_RESOLVIDO);
            resposta.setSolucao(true);

            return ResponseEntity.ok(new DadosSucesso("Status alterado com sucesso!"));
        }
    }










