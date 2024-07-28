package com.codewithprojects.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.codewithprojects.entities.User;
import com.codewithprojects.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {
	
	private final UserRepository userRepository;
	public static final String SECRET = "secret";

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
//	public User getLoggedInUser() {
//	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	    if (authentication != null && authentication.isAuthenticated()) {
//	        Object principal = authentication.getPrincipal();
//	        if (principal instanceof User) {
//	            User user = (User) principal;
//	            Optional<User> optionalUser = userRepository.findById(user.getId());
//	            return optionalUser.orElse(null);
//	        } else {
//	            String username = principal.toString();
//	            Optional<User> optionalUser = userRepository.findFirstByEmail(username);
//	            return optionalUser.orElse(null);
//	        }
//	    }
//	    return null;
//	}

	public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
        	Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        	String username = loggedInUser.getName();
        	System.out.println(username);
            //Optional<User> optionalUser = userRepository.findById(user.getId());
            //return optionalUser.orElse(null);
        }
        return null;
    }


}
