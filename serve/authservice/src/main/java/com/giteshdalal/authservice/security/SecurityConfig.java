package com.giteshdalal.authservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author gitesh
 *
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userServiceImpl")
	public UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService) // using user service
				.passwordEncoder(passwordEncoder); // password encoder
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().disable() // disable form authentication
				.anonymous().disable() // disable anonymous user
				.authorizeRequests().anyRequest().denyAll(); // denying all access
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// provides the default AuthenticationManager as a Bean
		return super.authenticationManagerBean();
	}
}
