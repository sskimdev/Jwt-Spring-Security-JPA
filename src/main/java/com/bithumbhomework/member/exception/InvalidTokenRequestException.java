package com.bithumbhomework.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenRequestException extends RuntimeException {

//	private final String tokenType;
//	private final String token;
//	private final String message;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5402926361693331186L;

	public InvalidTokenRequestException(String tokenType, String token, String message) {
		super(String.format("%s: [%s] token: [%s] ", message, tokenType, token));
//		this.tokenType = tokenType;
//		this.token = token;
//		this.message = message;
	}
}
