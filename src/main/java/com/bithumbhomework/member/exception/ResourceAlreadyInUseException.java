package com.bithumbhomework.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyInUseException extends RuntimeException {

	private final String resourceName;
	private final String fieldName;
	private final transient Object fieldValue;

	public ResourceAlreadyInUseException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("[Error] 이미 존재하는 값입니다. %s = '%s'", resourceName, fieldValue));
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