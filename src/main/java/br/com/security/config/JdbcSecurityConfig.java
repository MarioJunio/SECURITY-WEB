package br.com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

//@Configuration
public class JdbcSecurityConfig {

    public static final String USUARIO_LOGIN = "select a.login, a.senha, a.ativo from usuario a where a.login = ?";
    public static final String PERMISSAO_USUARIO = "select u.login, p.nome as nome_permissao from usuario_permissao up join usuario u on (u.id = up.usuario_id) join permissao p on (p.id = up.permissao_id) where lower(u.login) = lower(?)";
    public static final String PERMISSOES_GRUPO = "select g.id, g.nome, p.nome from grupo_permissao gp join grupo g on (g.id = gp.grupo_id) join permissao p on (p.id = gp.permissao_id) join usuario_grupo ug on (ug.usuario_id = g.id) join usuario u on (u.id = ug.usuario_id) where u.login = ?";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder, PasswordEncoder encoder, DataSource dataSource) throws Exception {
        builder
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder)
                .usersByUsernameQuery(USUARIO_LOGIN)
                .authoritiesByUsernameQuery(PERMISSAO_USUARIO);
    }

    public static void main(String[] args) {
        System.out.println(new JdbcSecurityConfig().passwordEncoder().encode("test"));
    }

}
