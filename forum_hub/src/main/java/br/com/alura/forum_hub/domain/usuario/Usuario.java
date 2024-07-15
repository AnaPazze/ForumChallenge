package br.com.alura.forum_hub.domain.usuario;


import br.com.alura.forum_hub.domain.curso.Curso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Usuario extends RepresentationModel<Curso> implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String login;

    private String senha;


    public Usuario(DadosCadastroUsuario dadosCadastroUsuario){
        this.id = null;
        this.nome = dadosCadastroUsuario.nome();
        this.login = dadosCadastroUsuario.login();
        this.senha = dadosCadastroUsuario.senha();


    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public static Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean temPermisaoParaModificacao(Usuario usuario) {
        return Objects.equals(Usuario.getUsuarioLogado().getId(), usuario.getId());
    }
    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void atualizarInformacoes(DadosAtualizacaoUsuario dadosAtualizacaoUsuario) {
        if (dadosAtualizacaoUsuario.nome() != null) {
            this.nome = dadosAtualizacaoUsuario.nome();
        }
        if (dadosAtualizacaoUsuario.login() != null) {
            this.login = dadosAtualizacaoUsuario.login();
        }
        if (dadosAtualizacaoUsuario.senha() != null) {
            this.senha = criptografarSenha(dadosAtualizacaoUsuario.senha());
        }
    }

    private String criptografarSenha(String senha){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(senha);
    }
}










