package com.bithumbhomework.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends AccessDeniedException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4738113941145864852L;

	public UnauthorizedException() {
		super("로그인 되지 않은 사용자입니다.");
	}

}
