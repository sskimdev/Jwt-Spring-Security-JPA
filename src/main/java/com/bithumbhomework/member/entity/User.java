
package com.bithumbhomework.member.entity;

import org.hibernate.annotations.NaturalId;

import com.bithumbhomework.member.entity.audit.DateAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity(name = "USER")
public class User extends DateAudit {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", allocationSize = 1)
	private Long id;

	@NaturalId
	@Column(name = "EMAIL", unique = true)
	@NotNull(message = "Mandatory")
	private String email;

	@Column(name = "USERNAME")
	@NotNull(message = "Mandatory")
	private String username;

	@Column(name = "PASSWORD")
	@NotNull(message = "Mandatory")
	private String password;

	public User() {
		super();
	}

	public User(User user) {
		id = user.getId();
		username = user.getUsername();
		password = user.getPassword();
		email = user.getEmail();
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password + "]";
	}
}
