package br.com.security.config;

import br.com.security.security.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailsService appUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**").permitAll().antMatchers("/img/**").permitAll()
				.antMatchers("/").authenticated().antMatchers("/clientes").hasAnyRole("FUNC").antMatchers("/empregados")
				.hasAnyRole("FUNC").antMatchers("/novo-cliente").hasAnyRole("FUNC_1").antMatchers("/novo-empregado")
				.hasAnyRole("FUNC_1").anyRequest().authenticated().and().formLogin().loginPage("/auth").permitAll()
				.and().logout().logoutSuccessUrl("/auth?logout").permitAll().and().rememberMe()
				.userDetailsService(appUserDetailsService);

	}

}
