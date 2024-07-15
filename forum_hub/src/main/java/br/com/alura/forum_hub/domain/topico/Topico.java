package br.com.alura.forum_hub.domain.topico;

import br.com.alura.forum_hub.domain.curso.Curso;
import br.com.alura.forum_hub.domain.resposta.Resposta;
import br.com.alura.forum_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.transaction.Status;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static jakarta.transaction.Status.*;


@Entity(name = "Topico")
@Table(name = "topicos")
@AllArgsConstructor
@NoArgsConstructor
@Getter

@EqualsAndHashCode(of = "id", callSuper = false)
public class Topico extends RepresentationModel<Topico> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String titulo;

    @Column(unique = true, nullable = false)
    private String mensagem;

    private LocalDateTime dataCriacao;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private Topico topico;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas;



    public Topico(String titulo, String mensagem,Curso curso) {

        this.id = null;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
        this.usuario = Usuario.getUsuarioLogado();
        this.curso = curso;
       // this.status = Status.NAO_RESOLVIDO;


    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dadosAtualizacaoTopico) {
        Optional<String> optionalTitulo = Optional.ofNullable(dadosAtualizacaoTopico.titulo());
        optionalTitulo.ifPresent(t -> this.titulo = t);

        Optional<String> optionalMensagem = Optional.ofNullable(dadosAtualizacaoTopico.mensagem());
        optionalMensagem.ifPresent(m -> this.mensagem = m);
    }

    public void setStatus(br.com.alura.forum_hub.domain.status.Status status) {

    }
}



