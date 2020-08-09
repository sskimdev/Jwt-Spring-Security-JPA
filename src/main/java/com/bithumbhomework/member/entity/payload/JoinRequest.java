package com.bithumbhomework.member.entity.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.bithumbhomework.member.validation.annotation.NullOrNotBlank;

@ApiModel(value = "Join Request", description = "The join request payload")
public class JoinRequest {

	@NotNull(message = "Mandatory")
	@ApiModelProperty(value = "회원 이름", required = true, allowableValues = "")
	private String username;

	@NotNull(message = "Mandatory")
	@Email(message = "이메일 형식을 맞춰주세요.")
	@ApiModelProperty(value = "회원 이메일", required = true, allowableValues = "email format ..")
	private String email;

	@NotNull(message = "Mandatory")
	@ApiModelProperty(value = "비밀번호 (영어 대/소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상)", required = true, allowableValues = "")
	private String password;

	public JoinRequest() {
//		super();
		// TODO Auto-generated constructor stub
	}

	public JoinRequest(String username, String email, String password) {
//		super();
		this.username = username;
		this.email = email;
		this.password = password;
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
		return "JoinRequest [username=" + username + ", email=" + email + ", password=" + password + "]";
	}

//    @NotNull(message = "Specify whether the user has to be registered as an admin or not")
//    @ApiModelProperty(value = "Flag denoting whether the user is an admin or not", required = true,
//            dataType = "boolean", allowableValues = "true, false")
//    private Boolean registerAsAdmin;

//    public JoinRequest(String username, String email,
//                               String password) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
////        this.registerAsAdmin = registerAsAdmin;
//    }
//
//    public JoinRequest() {
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

//    public Boolean getRegisterAsAdmin() {
//        return registerAsAdmin;
//    }
//
//    public void setRegisterAsAdmin(Boolean registerAsAdmin) {
//        this.registerAsAdmin = registerAsAdmin;
//    }
}
