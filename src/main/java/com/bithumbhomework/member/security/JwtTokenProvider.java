package com.bithumbhomework.member.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bithumbhomework.member.entity.CustomUserDetails;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

	private final String jwtSecret;
	private final long jwtExpirationInMs;

	public JwtTokenProvider(@Value("${app.jwt.secret}") String jwtSecret,
			@Value("${app.jwt.expiration}") long jwtExpirationInMs) {
		this.jwtSecret = jwtSecret;
		this.jwtExpirationInMs = jwtExpirationInMs;
	}

	public String generateToken(CustomUserDetails customUserDetails) {
		Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
		return Jwts.builder().setSubject(Long.toString(customUserDetails.getId())).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(expiryDate)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String generateTokenFromUserId(Long userId) {
		Instant expiryDate = Instant.now().plusMillis(jwtExpirationInMs);
		return Jwts.builder().setSubject(Long.toString(userId)).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(expiryDate)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	public Date getTokenExpiryFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

		return claims.getExpiration();
	}

	public long getExpiryDuration() {
		return jwtExpirationInMs;
	}
}
