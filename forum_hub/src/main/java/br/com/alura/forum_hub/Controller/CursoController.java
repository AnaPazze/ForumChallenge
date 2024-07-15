package br.com.alura.forum_hub.Controller;

import br.com.alura.forum_hub.domain.curso.CursoService;
import br.com.alura.forum_hub.domain.curso.DadosAtualizacaoCurso;
import br.com.alura.forum_hub.domain.curso.DadosCadastroCurso;
import br.com.alura.forum_hub.domain.curso.DadosListagemCurso;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

public class CursoController {

    @Autowired
    CursoService cursoService;

    @Operation(summary = "Cadastre um curso", description = "Cadastre um curso")
    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastroCurso(@RequestBody @Valid DadosCadastroCurso dadosCadastroCurso, UriComponentsBuilder uriComponentsBuilder){
        return cursoService.cadastrarCurso(dadosCadastroCurso,uriComponentsBuilder);
    }

    @Operation(summary = "Liste todos os cursos", description = "Liste todos os cursos")
    @GetMapping
    public ResponseEntity<Page<DadosListagemCurso>> listarTodosCursos(Pageable pageable){
        return cursoService.listarTodosCursos(pageable);
    }

    @Operation(summary = "Liste um curso específico", description = "Liste um curso específico")
    @GetMapping("/{id}")
    public ResponseEntity<?> listarCurso(@PathVariable(name = "id") UUID id){
        return cursoService.listarCurso(id);
    }

    @Operation(summary = "Atualize um curso específico", description = "Atualize um curso específico")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarCurso(@RequestBody DadosAtualizacaoCurso dadosAtualizacaoCurso, @PathVariable(name = "id") UUID id){
        return cursoService.autualizarCurso(dadosAtualizacaoCurso, id);
    }

    @Operation(summary = "Delete um curso específico", description = "Delete um curso específico")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluirCurso(@PathVariable(name = "id") UUID id){
        return cursoService.excluirCurso(id);
    }
}














