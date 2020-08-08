
package com.bithumbhomework.member.entity;

import org.hibernate.annotations.NaturalId;

import com.bithumbhomework.member.entity.audit.DateAudit;
import com.bithumbhomework.member.validation.annotation.NullOrNotBlank;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "USER")
public class User extends DateAudit {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long id;

    @NaturalId
    @Column(name = "EMAIL", unique = true)
    @NotBlank(message = "User email cannot be null")
    private String email;

    @Column(name = "USERNAME", unique = true)
    @NullOrNotBlank(message = "Username can not be blank")
    private String username;

    @Column(name = "PASSWORD")
    @NotNull(message = "Password cannot be null")
    private String password;


//    @Column(name = "IS_ACTIVE", nullable = false)
//    private Boolean active;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "USER_AUTHORITY", joinColumns = {
//            @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")}, inverseJoinColumns = {
//            @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")})
//    private Set<Role> roles = new HashSet<>();

//    @Column(name = "IS_EMAIL_VERIFIED", nullable = false)
//    private Boolean isEmailVerified;

    public User() {
        super();
    }

    public User(User user) {
        id = user.getId();
        username = user.getUsername();
        password = user.getPassword();
        email = user.getEmail();
//        active = user.getActive();
//        roles = user.getRoles();
//        isEmailVerified = user.getIsEmailVerified();
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

//	/**
//	 * @return the active
//	 */
//	public Boolean getActive() {
//		return active;
//	}
//
//	/**
//	 * @param active the active to set
//	 */
//	public void setActive(Boolean active) {
//		this.active = active;
//	}

//	/**
//	 * @return the roles
//	 */
//	public Set<Role> getRoles() {
//		return roles;
//	}
//
//	/**
//	 * @param roles the roles to set
//	 */
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}

//	/**
//	 * @return the isEmailVerified
//	 */
//	public Boolean getIsEmailVerified() {
//		return isEmailVerified;
//	}
//
//	/**
//	 * @param isEmailVerified the isEmailVerified to set
//	 */
//	public void setIsEmailVerified(Boolean isEmailVerified) {
//		this.isEmailVerified = isEmailVerified;
//	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password + "]";
	}
}
