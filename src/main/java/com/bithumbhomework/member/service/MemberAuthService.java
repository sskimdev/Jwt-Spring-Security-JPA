package com.bithumbhomework.member.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bithumbhomework.member.entity.CustomUserDetails;
import com.bithumbhomework.member.entity.User;
import com.bithumbhomework.member.entity.UserLogin;
import com.bithumbhomework.member.entity.payload.LoginRequest;
import com.bithumbhomework.member.entity.payload.JoinRequest;
import com.bithumbhomework.member.entity.token.RefreshToken;
import com.bithumbhomework.member.exception.InvalidFormatRequestException;
import com.bithumbhomework.member.exception.ResourceAlreadyInUseException;
import com.bithumbhomework.member.security.JwtTokenProvider;
import com.bithumbhomework.member.util.Util;

import java.util.Optional;

@Service
public class MemberAuthService {

	private static final Logger logger = Logger.getLogger(MemberAuthService.class);
	private final UserService userService;
	private final JwtTokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserLoginService userLoginService;

	@Autowired
	public MemberAuthService(UserService userService, JwtTokenProvider tokenProvider, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, UserLoginService userLoginService,
			RefreshTokenService refreshTokenService) {
		this.userService = userService;
		this.tokenProvider = tokenProvider;
		this.refreshTokenService = refreshTokenService;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.userLoginService = userLoginService;
	}

	/**
	 * 신규 회원 가입
	 *
	 * @return A user object if successfully created
	 */
	public Optional<User> joinUser(JoinRequest newRegistrationRequest) {
		String newRegistrationRequestEmail = newRegistrationRequest.getEmail();
		if (emailAlreadyExists(newRegistrationRequestEmail)) {
			logger.error("Email already exists: " + newRegistrationRequestEmail);
			throw new ResourceAlreadyInUseException("Email", "Address", newRegistrationRequestEmail);
		}
		if (!validateEmailFormat(newRegistrationRequestEmail)) {
			logger.error("Email invalid: " + newRegistrationRequestEmail);
			throw new InvalidFormatRequestException("Email", newRegistrationRequestEmail);
		}
		logger.info("Trying to register new user [" + newRegistrationRequestEmail + "]");
		User newUser = userService.createUser(newRegistrationRequest);
		logger.info("Trying to register new user [" + newUser + "]");
		User registeredNewUser = userService.save(newUser);
		return Optional.ofNullable(registeredNewUser);
	}

	/**
	 * 가입하고자 하는 이메일로 이미 가입된 회원이 있는지 체크
	 *
	 * @return true if the email exists else false
	 */
	public Boolean emailAlreadyExists(String email) {
		return userService.existsByEmail(email);
	}

	/**
	 * 유효한 이메일 형식인지 체크
	 *
	 * @return true if the email exists else false
	 */
	public Boolean validateEmailFormat(String email) {
		return Util.validateEmailFormat(email);
	}
	
	/**
	 * 비밀번호 생성 규칙 (영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성) 
	 *
	 * @return true if the email exists else false
	 */
	public Boolean validatePasswordRule(String password) {
		return Util.validatePasswordRule(password);
	}

	/**
	 * 회원 인증 요청
	 */
	public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
		return Optional.ofNullable(authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())));
	}

	/**
	 * 패스워드 검증 
	 */
	@SuppressWarnings("unused")
	private Boolean currentPasswordMatches(User currentUser, String password) {
		return passwordEncoder.matches(password, currentUser.getPassword());
	}

	/**
	 * Generates a JWT token by CustomUserDetails
	 */
	public String generateToken(CustomUserDetails customUserDetails) {
		return tokenProvider.generateToken(customUserDetails);
	}

	/**
	 * Generates a JWT token by user id
	 */
	@SuppressWarnings("unused")
	private String generateTokenFromUserId(Long userId) {
		return tokenProvider.generateTokenFromUserId(userId);
	}

	/**
	 * 로그인 유저를 위한 refresh token 생성 및 저장
	 */
	public Optional<RefreshToken> createAndPersistRefreshTokenForLogin(Authentication authentication,
			LoginRequest loginRequest) {
		User currentUser = (User) authentication.getPrincipal();
		userLoginService.findByUserId(currentUser.getId()).map(UserLogin::getRefreshToken).map(RefreshToken::getId)
				.ifPresent(refreshTokenService::deleteById);

//		UserLogin userLogin = userLoginService.createUserLogin(loginRequest.getLoginInfo());
		UserLogin userLogin = userLoginService.createUserLogin(Util.generateRandomUuid());
		RefreshToken refreshToken = refreshTokenService.createRefreshToken();
		userLogin.setUser(currentUser);
		userLogin.setRefreshToken(refreshToken);
		refreshToken.setUserLogin(userLogin);
		refreshToken = refreshTokenService.save(refreshToken);
		return Optional.ofNullable(refreshToken);
	}

}
