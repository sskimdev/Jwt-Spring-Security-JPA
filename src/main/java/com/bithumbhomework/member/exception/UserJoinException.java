package com.bithumbhomework.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserJoinException extends RuntimeException {

	private final String user;
	private final String message;

	public UserJoinException(String user, String message) {
		super(String.format("Failed to register User[%s] : '%s'", user, message));
		this.user = user;
		this.message = message;
	}

}