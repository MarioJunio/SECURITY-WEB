package br.com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

//@Configuration
public class InMemorySecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder
                .inMemoryAuthentication()
                .withUser("mario").password("1234").roles("ADMIN", "FUNC")
                .and()
                .withUser("test").password("test").roles("FUNC");
    }

}
