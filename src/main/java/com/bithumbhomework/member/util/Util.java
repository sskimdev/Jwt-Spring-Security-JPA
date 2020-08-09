package com.bithumbhomework.member.util;

import java.util.UUID;

public class Util {

	private Util() {
		throw new UnsupportedOperationException("Cannot instantiate a Util class");
	}

	public static String generateRandomUuid() {
		return UUID.randomUUID().toString();
	}
}
