package com.bithumbhomework.member.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bithumbhomework.member.entity.CustomUserDetails;
import com.bithumbhomework.member.entity.User;
import com.bithumbhomework.member.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = Logger.getLogger(CustomUserDetailsService.class);
	private final UserRepository userRepository;

	@Autowired
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> dbUser = userRepository.findByEmail(email);
		logger.info("Fetched user : " + dbUser + " by " + email);
		return dbUser.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(
				"이메일이 일치하는 회원을 찾을수가 없습니다. email=" + email));
	}

	public UserDetails loadUserById(Long id) {
		Optional<User> dbUser = userRepository.findById(id);
		logger.info("Fetched user : " + dbUser + " by " + id);
		return dbUser.map(CustomUserDetails::new).orElseThrow(
				() -> new UsernameNotFoundException("ID가 일치하는 회원을 찾을수가 없습니다. id=" + id));
	}
}
