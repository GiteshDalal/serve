package com.giteshdalal.authservice.service;

import java.util.Optional;
import javax.security.auth.login.AccountException;

import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.resource.UserResource;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author gitesh
 */
public interface UserService extends UserDetailsService {

	@Override
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	UserModel findUserByUsername(String username) throws UsernameNotFoundException;

	UserModel register(String username, String email, String password) throws AccountException;

	UserModel register(UserModel user) throws AccountException;

	void changePassword(String oldPassword, String newPassword) throws AccountException;

	boolean userExistsWithUsername(String username);

	boolean userExistsWithEmail(String email);

	void removeAuthenticatedAccount() throws UsernameNotFoundException;

	Optional<UserResource> findUserById(Long uid);

	Page<UserResource> findAllUsers(Predicate predicate, Pageable pageable);

	UserResource saveUser(UserResource resource) throws AccountException;

	UserResource updateUser(Long uid, UserResource resource);

	void deleteUserById(Long uid);
}
