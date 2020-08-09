package com.bithumbhomework.member.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	private static final String emailRegExp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	
	private static final String pwNumberPattern = "(?=.*\\d)";     //0 ~ 9 까지 숫자 최소 한개
	private static final String pwLowerPattern = "(?=.*[a-z])";    //영문소문자 a ~ z 까지의 문자 최소 한개
	private static final String pwUpperPattern = "(?=.*[A-Z])";    //영문대문자 A ~ Z 까지의 문자 최소 한개
	private static final String pwSpecialPattern = "(?=.*[@$!%*#?&])";      //특수문문자 최소 한개
	private static final String pwLengthPattern = "(?=.{12,}$)";   //길이 12자리 이상 

	private Util() {
		throw new UnsupportedOperationException("Cannot instantiate a Util class");
	}

	public static String generateRandomUuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 유효한 이메일 형식인지 체크
	 *
	 * @return true if the email exists else false
	 */
	public static Boolean validateEmailFormat(String email) {
		return email.matches(emailRegExp);
	}
	
	/**
	 * 비밀번호 생성 규칙 (영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성) 
	 *
	 * @return true if the email exists else false
	 */
	public static Boolean validatePasswordRule(String password) {
		
		int patternCount = 0; 
		
		if(Pattern.compile(pwNumberPattern).matcher(password).find()) patternCount ++;
		if(Pattern.compile(pwLowerPattern).matcher(password).find()) patternCount ++;
		if(Pattern.compile(pwUpperPattern).matcher(password).find()) patternCount ++;
		if(Pattern.compile(pwSpecialPattern).matcher(password).find()) patternCount ++;
		
		if(patternCount < 3) {
			return false;
		}
		
		return Pattern.compile(pwLengthPattern).matcher(password).find();
	}
}
