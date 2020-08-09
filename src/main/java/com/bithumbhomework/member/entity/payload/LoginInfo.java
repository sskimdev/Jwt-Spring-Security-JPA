package com.bithumbhomework.member.entity.payload;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//import com.bithumbhomework.member.entity.DeviceType;
import com.bithumbhomework.member.validation.annotation.NullOrNotBlank;

public class LoginInfo {

	@NotBlank(message = "")
	@ApiModelProperty(value = "Login Id", required = true, dataType = "string", allowableValues = "")
	private String loginId;

	public LoginInfo() {
	}

	public LoginInfo(String loginId) {
		this.loginId = loginId;
	}

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

	@Override
	public String toString() {
		return "LoginInfo [loginId=" + loginId + "]";
	}
}
