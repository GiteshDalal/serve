package com.giteshdalal.authservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

/**
 * @author gitesh
 *
 */
@Entity(name = "User")
public class UserModel implements UserDetails {

	private static final long serialVersionUID = 101L;
	public static final String NAME = "User";

	@Id
	@TableGenerator(name = "userSeqGen", table = "ID_GEN", pkColumnValue = "USER_ID", initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "userSeqGen")
	@Getter
	@Setter
	private Long uid;

	@Getter
	@Setter
	private String password, firstName, lastName;

	@Getter
	@Setter
	@Column(unique = true, nullable = false)
	private String username, email;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

	@Setter
	private boolean accountNonExpired, accountNonLocked, credentialsNonExpired, enabled;

	public UserModel() {
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void grantAuthority(String authority) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		roles.add(authority);
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
		return authorities;
	}

}
