package com.giteshdalal.authservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author gitesh
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

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "uid"),
			inverseJoinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"))
	private List<RoleModel> roles;

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

	public void grantAuthority(RoleModel role) {
		if (this.roles == null) {
			this.roles = new ArrayList<>();
		}
		roles.add(role);
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			role.getPrivileges().forEach(p -> {
				authorities.add(new SimpleGrantedAuthority(role.getName() + ":" + p.getName()));
			});
		});
		return authorities;
	}

}
