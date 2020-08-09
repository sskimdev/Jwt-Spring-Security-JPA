package com.bithumbhomework.member.entity.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//import javax.validation.Valid;
import javax.validation.constraints.NotNull;

//import com.bithumbhomework.member.validation.annotation.NullOrNotBlank;

@ApiModel(value = "Login Request", description = "The login request payload")
public class LoginRequest {

	@NotNull(message = "Mandatory")
	@ApiModelProperty(value = "User email", required = true, allowableValues = "")
	private String email;

	@NotNull(message = "Mandatory")
	@ApiModelProperty(value = "User password", required = true, allowableValues = "")
	private String password;

//	@Valid
//	@NotNull(message = "Login info cannot be null")
//	@ApiModelProperty(value = "Loing info", required = true, dataType = "object", allowableValues = "A valid "
//			+ "loginInfo object")
//	private LoginInfo loginInfo;

	public LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
//		this.loginInfo = loginInfo;
	}

	public LoginRequest() {
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

//	/**
//	 * @return the loginInfo
//	 */
//	public LoginInfo getLoginInfo() {
//		return loginInfo;
//	}
//
//	/**
//	 * @param loginInfo the loginInfo to set
//	 */
//	public void setLoginInfo(LoginInfo loginInfo) {
//		this.loginInfo = loginInfo;
//	}

	@Override
	public String toString() {
		return "LoginRequest [email=" + email + ", password=" + password + "]";
	}

}
