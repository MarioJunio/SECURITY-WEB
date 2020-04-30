package br.com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.security.security.AppUserDetailsService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailsService appUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/resources/**").permitAll()
		.antMatchers("/img/**").permitAll()
		.antMatchers("/public/**").permitAll()
		.antMatchers("/download-apps").permitAll()
		.antMatchers("/").authenticated()
		.antMatchers("/clientes").hasAnyRole("FUNC")
		.antMatchers("/empregados").hasAnyRole("FUNC")
		.antMatchers("/novo-cliente").hasAnyRole("FUNC_1")
		.antMatchers("/novo-empregado").hasAnyRole("FUNC_1")
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/auth").permitAll()
		.and().logout().logoutSuccessUrl("/auth?logout")
		.permitAll()
		.and().rememberMe()
		.userDetailsService(appUserDetailsService);

	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder, PasswordEncoder passwordEncoder, AppUserDetailsService appUserDetailsService) throws Exception {
        builder.userDetailsService(appUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
