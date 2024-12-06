package com.todocodeacademy.springsecurity.security.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.todocodeacademy.springsecurity.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

//esto es un filtro que se ejecuta por cada request
public class JwtTokenValidator extends OncePerRequestFilter {
    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    //hacer un filtro interno
    //estos nunca pueden ser nulos esta libreria tiene que venir de spring
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        //el jwtToken viene desde la request escondido en la cabecera
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        //
        if (jwtToken !=null){
            //cuando mandamos un token por el encabezado usamos un tipo de tokenizacion que se llama bearer
            //este es un esquema de autorizacion, lo que hace,os es recibir primero la palabra bearer y luego el token
            // bearer nasdljfnasdnfladsfasdf (asi masomenos se veria)
            //por eso quitamos los primeros 7
            jwtToken = jwtToken.substring(7);
            //ya tenemos el token, ahora vemos que se verifique, si sale bien
            //ya nos da el token decodificado
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
            //si es valido ya lo tenemos

            //extraemos los permisos y el usuario
            String username = jwtUtils.extractUsername(decodedJWT);
            String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

            //ahora tenemos que pasarlo en el security context holder para no estar validando siempre
            //lo convertimos a GrantAuthority las authorities que tiene o que ya extrajimos del jwt
            Collection<? extends GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

            SecurityContext context = SecurityContextHolder.getContext();

            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,authoritiesList);

            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request,response);
    }

}
