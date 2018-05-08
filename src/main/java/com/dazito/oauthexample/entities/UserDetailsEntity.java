package com.dazito.oauthexample.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

/**
 * Created by daz on 29/06/2017.
 */
@Entity
@Table(name = "oauth_user_details")
public class UserDetailsEntity implements UserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8843913087606482906L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (StringUtils.isEmpty(this.authorities)) {
			return AuthorityUtils.createAuthorityList("ROLE_USER");
		} else {
			List<GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
			authoritiesList.addAll(AuthorityUtils.createAuthorityList("ROLE_USER"));
			return authoritiesList;
		}
	}

	@Id
	@Column(nullable = false, unique = true, length = 128)
	private String username;

	@Column(nullable = false, length = 128)
	private String password;
	
	@Column
	private String authorities;
	
	@Column
	private Date accountExpiredOn;
	
	@Column
	private Date credentialsExpiredOn;
	
	@Column
	private boolean locked;
	
	@Column
	private boolean enabled;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getAccountExpiredOn() {
		return accountExpiredOn;
	}

	public void setAccountExpiredOn(Date accountExpiredOn) {
		this.accountExpiredOn = accountExpiredOn;
	}

	public Date getCredentialsExpiredOn() {
		return credentialsExpiredOn;
	}

	public void setCredentialsExpiredOn(Date credentialsExpiredOn) {
		this.credentialsExpiredOn = credentialsExpiredOn;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return (accountExpiredOn==null || accountExpiredOn.after(new Date()));
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isAccountNonExpired() && (credentialsExpiredOn==null || credentialsExpiredOn.after(new Date()));
	}

	@Override
	public boolean isEnabled() {
		return isAccountNonLocked() && enabled ;
	}

}
