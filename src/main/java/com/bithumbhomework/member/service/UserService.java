package com.bithumbhomework.member.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bithumbhomework.member.entity.User;
import com.bithumbhomework.member.entity.payload.JoinRequest;
import com.bithumbhomework.member.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

//	private static final Logger logger = Logger.getLogger(UserService.class);
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@Autowired
	public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	/**
	 * 이메일을 이용한 회원 조회 
	 */
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * id를 이용한 회원 조회 
	 */
	public Optional<User> findById(Long Id) {
		return userRepository.findById(Id);
	}

	/**
	 * 회원 저장 
	 */
	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * 이메일을 이용한 회원 여부 체크 
	 */
	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	/**
	 * 신규 회원 추가 
	 */
	public User createUser(JoinRequest joinRequest) {
		User newUser = new User();
		newUser.setEmail(joinRequest.getEmail());
		newUser.setPassword(passwordEncoder.encode(joinRequest.getPassword()));
		newUser.setUsername(joinRequest.getUsername());
		return newUser;
	}

}
