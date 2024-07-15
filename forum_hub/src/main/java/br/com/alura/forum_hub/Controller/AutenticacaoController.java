package br.com.alura.forum_hub.Controller;


import br.com.alura.forum_hub.domain.status.DadosErros;
import br.com.alura.forum_hub.domain.usuario.DadosAutenticacao;
import br.com.alura.forum_hub.domain.usuario.Usuario;
import br.com.alura.forum_hub.infra.exception.TratadorDeErros;
import br.com.alura.forum_hub.infra.security.DadosTokenJWT;
import br.com.alura.forum_hub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Tag(name = "Publico Controller", description = "Endpoints Públicos")
    @Operation(summary = "Faça login", description = "Faça login na API e gere um Token JWT e insira em Authorize para ter acesso aos endpoints restritos")
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        if (tokenJWT == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErros("Erro ao gerar token JWT!"));


        }

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));


    }


}