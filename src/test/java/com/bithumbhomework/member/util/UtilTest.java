package com.bithumbhomework.member.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.bithumbhomework.member.util.Util;

public class UtilTest {
	
	@Test
	public void validateEmailFormat() {
		assertTrue(Util.validateEmailFormat("aaa@homework.com"));
	}
	
	@Test
	public void validatePasswordRule() {
		
		assertFalse(Util.validatePasswordRule("aaakorea.com1234"));
		assertFalse(Util.validatePasswordRule("1aA@"));
		assertFalse(Util.validatePasswordRule("1a@12345678"));
		assertTrue(Util.validatePasswordRule("1a@123456789"));
	}

}
