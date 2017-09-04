package br.com.security.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "grupo")
public class Grupo implements Serializable {

    private Long id;
    private String nome;
    private List<Permissao> permissoes;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @ManyToMany
    @JoinTable(name = "grupo_permissao", joinColumns = {@JoinColumn(name = "grupo_id")}, inverseJoinColumns = {@JoinColumn(name = "permissao_id")})
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

        Grupo grupo = (Grupo) o;

        return id != null ? id.equals(grupo.id) : grupo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
