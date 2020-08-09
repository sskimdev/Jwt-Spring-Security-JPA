package com.bithumbhomework.member.entity.token;

import org.hibernate.annotations.NaturalId;

import com.bithumbhomework.member.entity.UserLogin;
import com.bithumbhomework.member.entity.audit.DateAudit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.time.Instant;

@Entity(name = "REFRESH_TOKEN")
public class RefreshToken extends DateAudit {

	@Id
	@Column(name = "TOKEN_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
	@SequenceGenerator(name = "refresh_token_seq", allocationSize = 1)
	private Long id;

	@Column(name = "TOKEN", nullable = false, unique = true)
	@NaturalId(mutable = true)
	private String token;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_LOGIN_ID", unique = true)
	private UserLogin userLogin;

	@Column(name = "REFRESH_COUNT")
	private Long refreshCount;

	@Column(name = "EXPIRY_DT", nullable = false)
	private Instant expiryDate;

	public RefreshToken() {
	}

	public RefreshToken(Long id, String token, UserLogin userLogin, Long refreshCount, Instant expiryDate) {
		this.id = id;
		this.token = token;
		this.userLogin = userLogin;
		this.refreshCount = refreshCount;
		this.expiryDate = expiryDate;
	}

	public void incrementRefreshCount() {
		refreshCount = refreshCount + 1;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the userLogin
	 */
	public UserLogin getUserLogin() {
		return userLogin;
	}

	/**
	 * @param userLogin the userLogin to set
	 */
	public void setUserLogin(UserLogin userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * @return the refreshCount
	 */
	public Long getRefreshCount() {
		return refreshCount;
	}

	/**
	 * @param refreshCount the refreshCount to set
	 */
	public void setRefreshCount(Long refreshCount) {
		this.refreshCount = refreshCount;
	}

	/**
	 * @return the expiryDate
	 */
	public Instant getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "RefreshToken [id=" + id + ", token=" + token + ", userLogin=" + userLogin + ", refreshCount="
				+ refreshCount + ", expiryDate=" + expiryDate + "]";
	}

}