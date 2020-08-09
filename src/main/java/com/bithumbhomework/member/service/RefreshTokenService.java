package com.bithumbhomework.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bithumbhomework.member.entity.token.RefreshToken;
import com.bithumbhomework.member.exception.TokenRefreshException;
import com.bithumbhomework.member.repository.RefreshTokenRepository;
import com.bithumbhomework.member.util.Util;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	@Value("${app.token.refresh.duration}")
	private Long refreshTokenDurationMs;

	@Autowired
	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	/**
	 * 로그인 토큰을 이용한 refresh token 조회 
	 */
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	/**
	 * refresh token 저장 
	 */
	public RefreshToken save(RefreshToken refreshToken) {
		return refreshTokenRepository.save(refreshToken);
	}

	/**
	 * refresh token 생성 
	 */
	public RefreshToken createRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(Util.generateRandomUuid());
		refreshToken.setRefreshCount(0L);
		return refreshToken;
	}

	/**
	 * refresh token 유효성 체크
	 */
	public void verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			throw new TokenRefreshException(token.getToken(), "Expired token : 토큰 발행을 다시 요청하세요.");
		}
	}

	/**
	 * refresh token 삭제 
	 */
	public void deleteById(Long id) {
		refreshTokenRepository.deleteById(id);
	}

	/**
	 * refresh token count 증가시키기 
	 */
	public void increaseCount(RefreshToken refreshToken) {
		refreshToken.incrementRefreshCount();
		save(refreshToken);
	}
}