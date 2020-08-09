package com.bithumbhomework.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bithumbhomework.member.entity.token.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	@Override
	Optional<RefreshToken> findById(Long id);

	Optional<RefreshToken> findByToken(String token);

}