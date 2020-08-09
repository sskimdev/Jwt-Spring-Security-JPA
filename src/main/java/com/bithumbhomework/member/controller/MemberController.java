package com.bithumbhomework.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bithumbhomework.member.annotation.CurrentUser;
import com.bithumbhomework.member.entity.CustomUserDetails;
import com.bithumbhomework.member.entity.UserLogin;
import com.bithumbhomework.member.entity.payload.ApiResponse;
import com.bithumbhomework.member.entity.payload.JwtAuthenticationResponse;
import com.bithumbhomework.member.entity.payload.LoginRequest;
import com.bithumbhomework.member.entity.payload.MemberInfoResponse;
import com.bithumbhomework.member.entity.payload.JoinRequest;
import com.bithumbhomework.member.exception.UserLoginException;
import com.bithumbhomework.member.exception.UserJoinException;
import com.bithumbhomework.member.security.JwtTokenProvider;
import com.bithumbhomework.member.service.MemberAuthService;
import com.bithumbhomework.member.service.UserLoginService;
import com.bithumbhomework.member.service.UserService;

import javax.validation.Valid;

import java.time.Instant;
import java.util.Optional;

@RestController
@RequestMapping("${rest.api.ver}/member")
@Api(value = "Authorization Rest API", description = "회원가입, 로그인, 회원 조회 API")

public class MemberController {

	private static final Logger logger = Logger.getLogger(MemberController.class);
	private final MemberAuthService memberService;
	private final UserService userService;
	private final UserLoginService userLoginService;
	private final JwtTokenProvider tokenProvider;
//	private final ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	public MemberController(MemberAuthService memberService, UserService userService, UserLoginService userLoginService,
			JwtTokenProvider tokenProvider) {
		this.memberService = memberService;
		this.userService = userService;
		this.userLoginService = userLoginService;
		this.tokenProvider = tokenProvider;
//		this.applicationEventPublisher = applicationEventPublisher;
	}

	/**
	 * 1. 회원가입 URI는 다음과 같습니다. : /v1/member/join 
	 * 2. 회원가입 시 필요한 정보는 ID, 비밀번호, 사용자이름 입니다. 
	 * 3. ID는 반드시 email 형식이어야 합니다. 
	 * 4. 비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다. 
	 * 5. 비밀번호는 서버에 저장될 때에는 반드시 단방향 해시 처리가 되어야 합니다.
	 */
	@PostMapping("/join")
	@ApiOperation(value = "회원 가입")
	public ResponseEntity joinUser(
			@ApiParam(value = "The JoinRequest payload") @Valid @RequestBody JoinRequest joinRequest) {

		return memberService.joinUser(joinRequest).map(user -> {
			logger.info("joinUser ==> " + user);
			return ResponseEntity.ok(new ApiResponse(true, "회원가입이 성공하였습니다."));
		}).orElseThrow(() -> new UserJoinException(joinRequest.getEmail(), "Missing user object in database"));
	}

	/**
	 * 1. 로그인 URI는 다음과 같습니다. : /v1/member/login 
	 * 2. 사용자로부터 ID, 비밀번호를 입력받아 로그인을 처리합니다
	 * 3. ID와 비밀번호가 이미 가입되어 있는 회원의 정보와 일치하면 로그인이 되었다는 응답으로 AccessToken을 제공합니다
	 */
	@PostMapping("/login")
	@ApiOperation(value = "회원 로그인")
	public ResponseEntity authenticateUser(
			@ApiParam(value = "The LoginRequest payload") @Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = memberService.authenticateUser(loginRequest)
				.orElseThrow(() -> new UserLoginException("회원 로그인 실패 [" + loginRequest + "]"));

		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		logger.info("Logged in User : " + customUserDetails.getEmail());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return memberService.createAndPersistRefreshTokenForLogin(authentication, loginRequest)
				.map(refreshToken -> {
					String jwtToken = memberService.generateToken(customUserDetails);
					String _refreshToken = refreshToken.getToken();
					
					return ResponseEntity.ok(
							new JwtAuthenticationResponse(jwtToken, _refreshToken, tokenProvider.getExpiryDuration()));
				})
				.orElseThrow(() -> new UserLoginException("refresh token을 생성할 수 없습니다. loginRequest : [" + loginRequest + "]"));
	}

	/**
	 * 1. 회원정보 조회 URI는 다음과 같습니다. : /v1/member/info 
	 * 2. 로그인이 된 사용자에 대해서는 사용자이름, Email, 직전 로그인 일시를 제공합니다. 
	 * 3. 로그인이 안된 사용자는 HTTP Status Code를 401 (Unauthorized)로 응답합니다.
	 */
	@GetMapping("/info")
	@ApiOperation(value = "회원정보 조회")
	public ResponseEntity getUserProfile(@CurrentUser CustomUserDetails currentUser) {
		logger.info(currentUser.getEmail());
		
		return userService.findById(currentUser.getId()).map(_user -> {
			Instant _lastLoginedAt  = userLoginService.findByUserId(_user.getId()).map(UserLogin::getUpdatedAt).orElseThrow(
					() -> new UsernameNotFoundException("Couldn't find a matching user id in the database for " + _user.getId()));;
			return ResponseEntity.ok(new MemberInfoResponse(_user.getUsername(), _user.getEmail(), _lastLoginedAt));
		}).orElseThrow(
				() -> new UserLoginException("Couldn't find a matching user id in the database for " + currentUser.getId()));
	}

}
