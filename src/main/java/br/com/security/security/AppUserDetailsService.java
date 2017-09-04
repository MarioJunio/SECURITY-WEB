package br.com.security.security;

import br.com.security.model.Permissao;
import br.com.security.model.Usuario;
import br.com.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        try {
            AppUserDetails userDetails = buscarUsuario(login);
            return userDetails;
        } catch (UsernameNotFoundException e) {
            throw e;
        }
    }

    private AppUserDetails buscarUsuario(String login) {

        Usuario usuario = usuarioRepository.findByLogin(login);

        if (usuario == null)
            throw new UsernameNotFoundException("Usuário " + login + " não encontrado");

        AppUserDetails appUserDetails = new AppUserDetails(usuario.getLogin(), usuario.getSenha(), usuario.isAtivo());

        for (Permissao permissao : usuario.getPermissoes())
            appUserDetails.getAuthorities().add(new SimpleGrantedAuthority(permissao.getNome()));

        return appUserDetails;
    }

}
