package com.bithumbhomework.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithumbhomework.member.entity.UserLogin;
import com.bithumbhomework.member.entity.payload.LoginInfo;
import com.bithumbhomework.member.repository.UserLoginRepository;

import java.util.Optional;

@Service
public class UserLoginService {

	private final UserLoginRepository userLoginRepository;

	@Autowired
	public UserLoginService(UserLoginRepository userLoginRepository) {
		this.userLoginRepository = userLoginRepository;
	}

	/**
	 * 회원id를 이용한 회원 로그인 정보 조회 
	 */
	public Optional<UserLogin> findByUserId(Long userId) {
		return userLoginRepository.findByUserId(userId);
	}

	/**
	 * 로그인된 회원의 로그인 정보 추가
	 */
	public UserLogin createUserLogin(LoginInfo loginInfo) {
		UserLogin userLogin = new UserLogin();
		userLogin.setLoginId(loginInfo.getLoginId());
		userLogin.setIsRefreshActive(true);
		return userLogin;
	}
	
	/**
	 * 로그인된 회원의 로그인 정보 추가
	 */
	public UserLogin createUserLogin(String loginId) {
		UserLogin userLogin = new UserLogin();
		userLogin.setLoginId(loginId);
		userLogin.setIsRefreshActive(true);
		return userLogin;
	}

}
