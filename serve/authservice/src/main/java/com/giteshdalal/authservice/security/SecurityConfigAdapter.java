package com.giteshdalal.authservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author gitesh
 * <p>
 * To see /oauth/authorize controller configuration
 * @see org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userService")
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
		http.authorizeRequests().antMatchers("/", "/index", "/assets/**", "/oauth/token_key", "/oauth/check_token", "/api/**")
				.permitAll().and().authorizeRequests().antMatchers("/oauth/authorize").hasRole("USER") // allow user
				.and().authorizeRequests().anyRequest().authenticated() // denying all other requests
				.and().formLogin().loginPage("/login").permitAll() // enabling login for authentication
				.and().logout().permitAll(); // allow everyone logout
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// provides the default AuthenticationManager as a Bean
		return super.authenticationManagerBean();
	}
}
