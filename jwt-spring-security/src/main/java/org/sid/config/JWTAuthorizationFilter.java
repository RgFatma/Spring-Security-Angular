package org.sid.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwtToken=request.getHeader("Authorization");
		
		if ( jwtToken==null ||!jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX)){
			filterChain.doFilter(request, response);return;
		}
		
		System.out.println("jwtToken"+jwtToken);
		
		//Sinon on va signer le token 
		
		Claims claims=Jwts.parser()
				.setSigningKey(SecurityConstants.SECRET)
				.parseClaimsJws(jwtToken.replace(SecurityConstants.TOKEN_PREFIX,""))
				.getBody();
		
		String username=claims.getSubject();
		
		ArrayList<Map<String,String>> roles=(ArrayList<Map<String,String>>)claims.get("roles");
		Collection<GrantedAuthority> authorities=new ArrayList<>();
		roles.forEach(r->{
			authorities.add(new SimpleGrantedAuthority(r.get("authority")));
		});
		
		UsernamePasswordAuthenticationToken authenticatedUser=
				new UsernamePasswordAuthenticationToken(username,null,authorities);
		
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		filterChain.doFilter(request,response);
		
		/*
		// Pour specifier les domaines qui ont l'authorisation d'accés //*: tout les domaines
		 
		response.addHeader("Access-Control-Allow-Origin","*");
		
		// les entetes que je l'authorise
		
		response.addHeader("Access-Control-Allow-Headers", "Cache-Control, Pragma, Origin, Accept, authorization, Access-Control-Request-Method,  Content-Type, X-Requested-With");
		
		// exposer les entetes, pour permettre le front end Angular de lire ces entetes
		 
		response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin,Access-Control-Allow-Credentials,authorisation");
		
		//Dans Angular avant que la requete POST est envoyée, Angular envoie un requete OPTIONS 
		  pour interroger le serveur
		 
		if(!(request.getMethod().equals("OPTIONS"))) {
			response.setStatus(HttpServletResponse.SC_OK);
		}*/
		
		/*if(!(request.getMethod().equalsIgnoreCase("OPTIONS"))) {
			try {
				filterChain.doFilter(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Pre-fight");
			response.setHeader("Access-Control-Allowed-Methods", "POST, GET, DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, x-auth-token, " +
                    "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
			response.setStatus(HttpServletResponse.SC_OK);
		}*/
		  
	}

}
