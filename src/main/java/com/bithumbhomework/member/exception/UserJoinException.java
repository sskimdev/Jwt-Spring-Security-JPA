package com.bithumbhomework.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserJoinException extends RuntimeException {

/**
	 * 
	 */
	private static final long serialVersionUID = -5117383042270998454L;

//	private final String user;
//	private final String message;

	public UserJoinException(String user, String message) {
		super(String.format("회원가입 실패[%s] : '%s'", user, message));
//		this.user = user;
//		this.message = message;
	}

}