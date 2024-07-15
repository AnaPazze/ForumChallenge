package br.com.alura.forum_hub.infra.exception;

import br.com.alura.forum_hub.domain.status.DadosErros;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> tratarErro404() {
        return ResponseEntity.notFound().build();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException exception){
        List<FieldError> erros =  exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<DadosErrosDuplicata> duplicata(SQLIntegrityConstraintViolationException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new DadosErrosDuplicata(exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> campoInvalido(IllegalArgumentException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErros(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> uuidInvalido(MethodArgumentTypeMismatchException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErros("ID inválido"));
    }

    public record DadosErrosValidacao(String campo, String mensagem){
        public DadosErrosValidacao(FieldError fieldError){
            this(fieldError.getField(),fieldError.getDefaultMessage());
        }
    }

    public record DadosErrosDuplicata(String error){

    }
}




