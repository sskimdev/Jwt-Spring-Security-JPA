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
	
	private final String resourceName;
	private final String fieldName;
	private final transient Object fieldValue;

	public InvalidFormatRequestException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("[Error] 유효하지 않은 값입니다... %s = '%s'", fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}
