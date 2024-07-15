package br.com.alura.forum_hub.domain.curso;


import br.com.alura.forum_hub.domain.categoria.Categoria;
import br.com.alura.forum_hub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity(name = "Curso")
@Table(name = "cursos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class Curso extends RepresentationModel<Curso> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos;


    public Curso(DadosCadastroCurso dadosCadastroCurso) {
        this.nome = dadosCadastroCurso.nome();
        this.categoria = Categoria.valueOf(dadosCadastroCurso.categoria());
    }

    public void atualizarInformacoes(DadosAtualizacaoCurso dadosAtualizacaoCurso) throws IllegalArgumentException{
        Optional<String> optionalNome = Optional.ofNullable(dadosAtualizacaoCurso.nome());
        optionalNome.ifPresent(n -> this.nome = n);

        Optional<String> optionalCategoria = Optional.ofNullable(dadosAtualizacaoCurso.categoria());
        optionalCategoria.ifPresent(c -> this.categoria = Categoria.valueOf(c));


    }
}







