package com.bithumbhomework.member.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bithumbhomework.member.exception.InvalidTokenRequestException;

//import java.util.Date;

@Component
public class JwtTokenValidator {

	private static final Logger logger = Logger.getLogger(JwtTokenValidator.class);
	private final String jwtSecret;

	@Autowired
	public JwtTokenValidator(@Value("${app.jwt.secret}") String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	/**
	 * 토큰 유효성 체크
	 */
	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
			throw new InvalidTokenRequestException("JWT", authToken, "Incorrect signature");

		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
			throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");

		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
			throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");

		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
			throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");

		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
			throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
		}
		return true;
	}

}
