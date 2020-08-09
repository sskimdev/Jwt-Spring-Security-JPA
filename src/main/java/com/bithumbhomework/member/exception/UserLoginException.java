package com.bithumbhomework.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UserLoginException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5976729766376190609L;

	public UserLoginException(String message) {
		super(message);
	}

	public UserLoginException(String message, Throwable cause) {
		super(message, cause);
	}
}