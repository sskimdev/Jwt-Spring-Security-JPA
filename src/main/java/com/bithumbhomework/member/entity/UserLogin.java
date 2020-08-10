package com.bithumbhomework.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.bithumbhomework.member.entity.audit.DateAudit;
import com.bithumbhomework.member.entity.token.RefreshToken;

@Entity(name = "USER_LOGIN")
public class UserLogin extends DateAudit {

	@Id
	@Column(name = "USER_LOGIN_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_login_seq")
	@SequenceGenerator(name = "user_login_seq", allocationSize = 1)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

//	@Column(name = "NOTIFICATION_TOKEN")
//	private String notificationToken;

	@Column(name = "LOGIN_ID", nullable = false)
	private String loginId;

	@OneToOne(optional = false, mappedBy = "userLogin")
	private RefreshToken refreshToken;

	@Column(name = "IS_REFRESH_ACTIVE")
	private Boolean isRefreshActive;

	public UserLogin() {
	}

	public UserLogin(Long id, User user, String notificationToken, String loginId, Boolean isRefreshActive) {
		this.id = id;
		this.user = user;
//		this.notificationToken = notificationToken;
		this.loginId = loginId;
		this.refreshToken = refreshToken;
		this.isRefreshActive = isRefreshActive;
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
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

//	/**
//	 * @return the notificationToken
//	 */
//	public String getNotificationToken() {
//		return notificationToken;
//	}
//
//	/**
//	 * @param notificationToken the notificationToken to set
//	 */
//	public void setNotificationToken(String notificationToken) {
//		this.notificationToken = notificationToken;
//	}

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the refreshToken
	 */
	public RefreshToken getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * @return the isRefreshActive
	 */
	public Boolean getIsRefreshActive() {
		return isRefreshActive;
	}

	/**
	 * @param isRefreshActive the isRefreshActive to set
	 */
	public void setIsRefreshActive(Boolean isRefreshActive) {
		this.isRefreshActive = isRefreshActive;
	}

	@Override
	public String toString() {
		return "UserLogin [id=" + id + ", user=" + user + ", loginId="
				+ loginId + ", refreshToken=" + refreshToken + ", isRefreshActive=" + isRefreshActive + "]";
	}

}
