package br.com.alura.forum_hub.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoRepository extends JpaRepository<Curso, UUID> {

    Curso findByNome(String curso);
}
