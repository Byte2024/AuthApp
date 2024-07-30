package com.riskanalysisexpertsystem.authApp.service;

import java.security.Key;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Map;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = "XP879ML7C1zJVontOD5l9AkH98luEHr5YGzeCxupZqL6umF1W6bRnXPZl2CGOiG8SBKo8vCVZRRKw6QiYI0C3UehqTpIiEsSAiSZmUbqe4ILsq3jiNeVH3b4Yfv9dgrEbDRPLRIEmCWeFvH1YSaFHsxjLsUicNOCrTDodwIzSRaHvRu1xa4OdzjebZ6n4nTZQ8Yuw9XYtLDOqQFxQjDsImU2SfHU2ccjq60pUzGrYVhuyniRp5RFmm2F0kWrGeb7\n";
	
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private Claims extractAllClaims(String token) {
		
		SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
		
		return Jwts.parser()
		        .verifyWith(secret)
		        .build()
		        .parseSignedClaims(token)
		        .getPayload();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails userDetails
			) {
		return Jwts.builder()
				.subject(userDetails.getUsername())
				.claims(extraClaims)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey())
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
