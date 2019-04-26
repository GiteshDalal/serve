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
		String[] permitAll = { "/", "/index", "/assets/**", "/oauth/token_key", "/oauth/check_token", "/api/**", "/register",
				"/forgot-password", "/reset-password" };
		String[] adminOnly = { "/dashboard", "client/**", "/role/**", "/privilege/**" };
		http.authorizeRequests().antMatchers(permitAll).permitAll() // permit all these urls
				.and().authorizeRequests().antMatchers("/oauth/authorize").hasRole("USER") // allow user
				.and().authorizeRequests().antMatchers(adminOnly).hasRole("ADMIN") // allow admin
				.and().authorizeRequests().anyRequest().authenticated() // denying all other requests
				.and().formLogin().loginPage("/login").permitAll() // enabling login for authentication
				.and().csrf().ignoringAntMatchers("/api/**", "/actuator/**") // ignore CSRFToken for APIs and logout
				.and().logout().logoutSuccessUrl("/login?logout"); // Redirect after logout
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// provides the default AuthenticationManager as a Bean
		return super.authenticationManagerBean();
	}
}
