package br.com.security.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class AppUserDetails implements UserDetails {

    private String login;
    private String senha;
    private boolean ativo;
    private Collection<GrantedAuthority> permissoes = new ArrayList<>();

    public AppUserDetails(String login, String senha, boolean ativo) {
        this.login = login;
        this.senha = senha;
        this.ativo = ativo;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return permissoes;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
