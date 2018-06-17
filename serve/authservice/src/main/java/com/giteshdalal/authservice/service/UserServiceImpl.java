package com.giteshdalal.authservice.service;

import java.util.Optional;

import javax.security.auth.login.AccountException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.repository.UserRepository;

/**
 * @author gitesh
 *
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserModel> user = userRepo.findOptionalByUsername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UsernameNotFoundException(String.format("Username [%s] not found", username));
		}
	}

	public UserModel findUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserModel> user = userRepo.findOptionalByUsername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UsernameNotFoundException(String.format("Username [%s] not found", username));
		}
	}

	public UserModel register(UserModel user) throws AccountException {
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getEmail())) {
			throw new AccountException("Username and Email can not be left blank");
		} else if (this.userExistsWithUsername(user.getUsername())) {
			throw new AccountException(String.format("Username [%s] is already taken", user.getUsername()));
		} else if (this.userExistsWithEmail(user.getEmail())) {
			throw new AccountException(
					String.format("Email [%s] is already associated with another account.", user.getEmail()));
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepo.save(user);
		}
	}

	@Transactional
	public void changePassword(String oldPassword, String newPassword) throws AccountException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = findUserByUsername(username);
		if (!passwordEncoder.encode(oldPassword).equals(user.getPassword())) {
			throw new AccountException("Password is incorrect.");
		} else {
			user.setPassword(passwordEncoder.encode(newPassword));
			userRepo.save(user);
		}
	}

	public boolean userExistsWithUsername(String username) {
		return userRepo.existsByUsername(username);
	}

	public boolean userExistsWithEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	@Transactional
	public void removeAuthenticatedAccount() throws UsernameNotFoundException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = findUserByUsername(username);
		userRepo.delete(user);
	}

}