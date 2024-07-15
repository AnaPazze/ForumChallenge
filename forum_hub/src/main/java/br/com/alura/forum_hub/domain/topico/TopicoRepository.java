package br.com.alura.forum_hub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, UUID> {
}
