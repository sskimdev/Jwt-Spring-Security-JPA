package com.bithumbhomework.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bithumbhomework.member.entity.UserLogin;
//import com.bithumbhomework.member.entity.token.RefreshToken;
import com.bithumbhomework.member.entity.token.RefreshToken;

import java.util.Optional;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

	@Override
	Optional<UserLogin> findById(Long id);

	Optional<UserLogin> findByRefreshToken(RefreshToken refreshToken);

	Optional<UserLogin> findByUserId(Long userId);
}
