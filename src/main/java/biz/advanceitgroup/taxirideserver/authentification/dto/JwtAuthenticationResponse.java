package biz.advanceitgroup.taxirideserver.authentification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;

import biz.advanceitgroup.taxirideserver.authentification.entities.RefreshToken;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.commons.entities.Parameters;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

	private String accessToken;

	private String refreshToken;
	
	private String phoneToken;
	
	private RefreshToken refreshTokens;

	private String tokenType;

	private Long expiryDuration;

	private String username; // Informations supplémentaires lors de la connexion de l'utilisateur

	private Collection<? extends GrantedAuthority> authorities;

	private String refreshTokenOpt;

	private Optional<Users> registeredUserOpt;

	private Long expiryDurations;

	private String phoneNumber;
	
	private ResponseEntity<?> verificationcode;
        
        private List<Parameters> basicParameters;

	public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiryDuration = expiryDuration;
		this.tokenType = "Bearer ";
	}
        public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration,List<Parameters> basicParameters) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiryDuration = expiryDuration;
		this.tokenType = "Bearer ";
                this.basicParameters=basicParameters;
	}
	
	public JwtAuthenticationResponse(String accessToken, RefreshToken refreshTokens, Long expiryDuration, String email, String phoneNumber ) {
		
		this.accessToken = accessToken;
		this.refreshTokens = refreshTokens;
		this.expiryDuration = expiryDuration;
		this.tokenType = "Bearer ";
		
		Collection<? extends GrantedAuthority> authorities = null;
		
		// informations supplémentaires lors de la connexion d'un utilisateur
		this.username = email;
		this.phoneNumber = phoneNumber;
		this.authorities = authorities;
	}

	// Constructeur qui permet de retourner les informations supplémentaires de l'utilisateur connecté (en plus du username et des rôles)
	public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration, String username, Collection<? extends GrantedAuthority> authorities) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiryDuration = expiryDuration;
		this.tokenType = "Bearer ";

		// informations supplémentaires lors de la connexion d'un utilisateur
		this.username = username;
		this.authorities = authorities;
	}
        
        public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration, String username, Collection<? extends GrantedAuthority> authorities,List<Parameters> basicParameters) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiryDuration = expiryDuration;
		this.tokenType = "Bearer ";

		// informations supplémentaires lors de la connexion d'un utilisateur
		this.username = username;
		this.authorities = authorities;
                this.basicParameters=basicParameters;
	}

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}
	public JwtAuthenticationResponse(String accessToken, Long expiryDuration) {
		this.accessToken = accessToken;
	}

	//Token with vérification code retourn
	public JwtAuthenticationResponse(String jwtToken, String refreshToken2, Long expiryDuration2, String username2,
			Collection<? extends GrantedAuthority> authorities2, ResponseEntity<?> registerphonenumberconfirmation) {
			this.accessToken = jwtToken;
			this.refreshToken = refreshToken2;
			this.expiryDuration = expiryDuration2;
			this.tokenType = "Bearer ";
	
			// informations supplémentaires lors de la connexion d'un utilisateur
			this.username = username2;
			this.authorities = authorities2;
			this.verificationcode = registerphonenumberconfirmation;
			
	}

	/*public JwtAuthenticationResponse(String refreshTokenOpt, Optional<Users> registeredUserOpt, Long expiryDuration2,
			String phoneNumber) {
			this.refreshTokenOpt = refreshTokenOpt;
			this.registeredUserOpt = registeredUserOpt;
			this.expiryDuration2 = expiryDuration2;
			this.tokenType = "Bearer ";
	
			// informations supplémentaires lors de la connexion d'un utilisateur
			this.phoneNumber = phoneNumber;
	}*/

}
