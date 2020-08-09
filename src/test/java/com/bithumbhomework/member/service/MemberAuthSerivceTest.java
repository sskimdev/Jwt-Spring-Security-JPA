package com.bithumbhomework.member.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.MockitoAnnotations;

import com.bithumbhomework.member.entity.CustomUserDetails;
import com.bithumbhomework.member.entity.User;
import com.bithumbhomework.member.security.JwtTokenProvider;
import com.bithumbhomework.member.service.MemberAuthService;

public class MemberAuthSerivceTest {
	
	private MemberAuthService memberAuthService;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
//		this.memberAuthService = new MemberAuthService();
	}
	
//	@Test
//	public void testValidateEmailFormat() {
//		assertTrue(this.email.matches(emailRegExp));
//	}

//	@Test
//	public void testGetUserIdFromJWT() {
//		String token = tokenProvider.generateToken(stubCustomUser());
//		assertEquals(100, tokenProvider.getUserIdFromJWT(token).longValue());
//	}
//
//	@Test
//	public void testGetTokenExpiryFromJWT() {
//		String token = tokenProvider.generateTokenFromUserId(120L);
//		assertNotNull(tokenProvider.getTokenExpiryFromJWT(token));
//	}
//
//	@Test
//	public void testGetExpiryDuration() {
//		assertEquals(jwtExpiryInMs, tokenProvider.getExpiryDuration());
//	}
//
//	private CustomUserDetails stubCustomUser() {
//		User user = new User();
//		user.setId((long) 100);
//		return new CustomUserDetails(user);
//	}

}
