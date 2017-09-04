package br.com.security.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private Long id;
    private String nome;
    private String login;
    private String senha;
    private boolean ativo;
    private List<Grupo> grupo;
    private List<Permissao> permissoes;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @ManyToMany
    @JoinTable(name = "usuario_grupo", joinColumns = {@JoinColumn(name = "usuario_id")}, inverseJoinColumns = {@JoinColumn(name = "grupo_id")})
    public List<Grupo> getGrupo() {
        return grupo;
    }

    public void setGrupo(List<Grupo> grupo) {
        this.grupo = grupo;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permissao", joinColumns = {@JoinColumn(name = "usuario_id")}, inverseJoinColumns = {@JoinColumn(name = "permissao_id")})
    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return id != null ? id.equals(usuario.id) : usuario.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
