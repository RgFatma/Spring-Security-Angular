package org.sid.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sid.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		AppUser appUser=null;
		try{
			//Recuperer les données username et password si elles sont envoyées en format x-www-format-encoded
			//String username=request.getParameter("userame");
			
			//Recuperer les données username et password si elles sont envoyées en format JSON
			//Passer par l'objet ObjectMapper pour désérialser les données et les stocker 
			//dans objet de type AppUser
			appUser=new ObjectMapper().readValue(request.getInputStream(),AppUser.class);
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		System.out.println("*************");
		System.out.println("username  "+appUser.getUsername());
		System.out.println("password  "+appUser.getPassword());

		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(),appUser.getPassword()));
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User springUser=(User)authResult.getPrincipal();
		//ajouter les données dans le header y compris le token généré
		String jwtToken=Jwts.builder()
				.setSubject(springUser.getUsername())
				.setExpiration(new
				Date (System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
				
				.signWith(SignatureAlgorithm.HS512,SecurityConstants.SECRET)
				.claim("roles",springUser.getAuthorities())
				.compact(); 
		response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+jwtToken);
		//c'est pas necessaire d'envoyer le prefix à l'utilisateur
		//par contre l'utilisateur doit envoyer le prefix que moi je le connais 
		//response.addHeader(SecurityConstants.HEADER_STRING,jwtToken);
		super.successfulAuthentication(request, response, chain, authResult);
	}
	
	
	
	
}
 