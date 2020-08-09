package com.bithumbhomework.member.entity.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "Token refresh Request", description = "The jwt token refresh request payload")
public class TokenRefreshRequest {

	@NotBlank(message = "Refresh token cannot be blank")
	@ApiModelProperty(value = "이전에 성공적으로 인증한 유효 토큰 전달", required = true, allowableValues = "")
	private String refreshToken;

	public TokenRefreshRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public TokenRefreshRequest() {
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
