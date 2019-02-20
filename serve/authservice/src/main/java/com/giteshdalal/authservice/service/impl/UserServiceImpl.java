package com.giteshdalal.authservice.service.impl;

import java.util.Optional;
import javax.security.auth.login.AccountException;

import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.repository.UserRepository;
import com.giteshdalal.authservice.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gitesh
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findUserByUsername(username);
	}

	@Override
	@Transactional
	public UserModel findUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserModel> user = userRepo.findOptionalByUsername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UsernameNotFoundException(String.format("Username [%s] not found", username));
		}
	}

	@Override
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
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setEnabled(true);
			return userRepo.save(user);
		}
	}

	@Override
	@Transactional
	public void changePassword(String oldPassword, String newPassword) throws AccountException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = findUserByUsername(username);
		if (!passwordEncoder.encode(oldPassword).equals(user.getPassword())) {
			throw new AccountException("Password is incorrect.");
		} else {
			user.setPassword(passwordEncoder.encode(newPassword));
			user.setCredentialsNonExpired(true);
			userRepo.save(user);
		}
	}

	@Override
	public boolean userExistsWithUsername(String username) {
		return userRepo.existsByUsername(username);
	}

	@Override
	public boolean userExistsWithEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	@Transactional
	public void removeAuthenticatedAccount() throws UsernameNotFoundException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = findUserByUsername(username);
		userRepo.delete(user);
	}

}