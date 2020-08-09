package com.bithumbhomework.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
//@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidFormatRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4738113941145864852L;
//	private final String fieldType;
//	private final transient Object fieldValue;
//	private final String message;

	public InvalidFormatRequestException(String fieldType, Object fieldValue) {
		super(String.format("[Error] 형식이 일치하지 않습니다. %s = '%s'", fieldType, fieldValue));
//		this.fieldType = fieldType;
//		this.fieldValue = fieldValue;
	}
}
