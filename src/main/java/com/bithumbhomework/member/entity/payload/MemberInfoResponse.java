package com.bithumbhomework.member.entity.payload;

import java.time.Instant;


public class MemberInfoResponse {

	private String username;

	private String email;

	private Instant lastLoginedAt;

	public MemberInfoResponse(String username, String email, Instant lastLoginedAt) {
		this.username = username;
		this.email = email;
		this.lastLoginedAt = lastLoginedAt;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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

	
}
