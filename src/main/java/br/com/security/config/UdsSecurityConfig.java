package br.com.security.config;

import br.com.security.security.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UdsSecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder, PasswordEncoder passwordEncoder, AppUserDetailsService appUserDetailsService) throws Exception {
        builder.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
