package com.bithumbhomework.member.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class CustomUserDetails extends User implements UserDetails {
	
	private Instant lastLoginedAt;

	public CustomUserDetails(final User user) {
		super(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority(super.getEmail()));
		return auth;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	/**
	 * @return the lastLoginedAt
	 */
	public Instant getLastLoginedAt() {
		return lastLoginedAt;
	}

	/**
	 * @param lastLoginedAt the lastLoginedAt to set
	 */
	public void setLastLoginedAt(Instant lastLoginedAt) {
		this.lastLoginedAt = lastLoginedAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		CustomUserDetails that = (CustomUserDetails) obj;
		return Objects.equals(getId(), that.getId());
	}
	
}
