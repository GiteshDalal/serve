package com.giteshdalal.authservice.service;

import javax.security.auth.login.AccountException;

import com.giteshdalal.authservice.model.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gitesh
 */
public interface UserService extends UserDetailsService {

	@Override
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	UserModel findUserByUsername(String username) throws UsernameNotFoundException;

	UserModel register(UserModel user) throws AccountException;

	@Transactional
	void changePassword(String oldPassword, String newPassword) throws AccountException;

	boolean userExistsWithUsername(String username);

	boolean userExistsWithEmail(String email);

	@Transactional
	void removeAuthenticatedAccount() throws UsernameNotFoundException;
}
