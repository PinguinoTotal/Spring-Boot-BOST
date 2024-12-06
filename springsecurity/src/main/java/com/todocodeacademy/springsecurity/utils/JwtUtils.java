package com.todocodeacademy.springsecurity.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

//para el uso de los JWT es necesairo ir a su pagina oficial JWT.io, filtrar por java, ir al repo de la primera opcion y
//agregar la dependencia en maven y volver a correr el programa

//se necesita una clave privada que sera la firma para que firme todos nuestros tokens eso se busca en la web como
//sha generator key, le puse suscribiteATodoCode y genero esto:
//f4e5d3bad303c742f671fbf03518e9bc94adf6921eacb60c1074496e143e3ce0 esto esta en .properties
//en este caso las puse asi de golpe porque en la calse estaban puestas en las variables de entorno con {security.jwt.private.key}
//y ${security.jwt.user.generator}

@Component
public class JwtUtils {


    //estos dos son para traer la clave privada para corroborar que fueron creados por estas y no hubo modificaciones
    //el value se pone asi porque el valor esta en las variables de entorno, pero lo pongo asi de golpe pos porque si por ahora
    //@Value("${security.jwt.private.key}")
    @Value("f4e5d3bad303c742f671fbf03518e9bc94adf6921eacb60c1074496e143e3ce0")
    private String privateKey;
    @Value("$TODOCODE-SEC")
    private String userGenerator;

    //metodo para creacion de tokens
    //Authentication es una interfaz que tiene spring security para el manejo de tokens
    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        //representa al usuario autenticado, nombre del usuario principal dentro de la autenticacion
        //queda en el context holder
        String userName = authentication.getPrincipal().toString();

        //necesitamos una lista de permisos separados por comas, pero .getAuthorities() devuelve una collection
        String authorities = authentication.getAuthorities()
                //lo convertimos en un stream para poder mapearlo
                .stream()
                //lo mapeaomos como GrantedAuthority para traer todos los Authorities
                .map(GrantedAuthority::getAuthority)
                //con un colector hacer que los colecte a todos y unirñps por una coma
                .collect(Collectors.joining(","));

        //generando el JWT
        String jwtToken = JWT.create()
                //quien esta creando este JWT?
                .withIssuer(this.userGenerator)
                //a quien se le esta generando este JWT? o que usuario va a viajar dentro de este token
                .withSubject(userName)
                // el claim o la informacion que lleva el JWT
                .withClaim("authorities", authorities)
                //cuando fue generado?
                .withIssuedAt(new Date())
                //cuando queremos que expire? en 30 minutos, esto esta dado en milisegundos
                .withExpiresAt(new Date(System.currentTimeMillis() + (30 * 60000)))
                //cada jwt tiene que tener su propio Id, asi que le generamos uno random y lo pasamos por string
                .withJWTId(UUID.randomUUID().toString())
                //desde cuando es valido este token?
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        return jwtToken;
    }

    //decodificar y validar nuestros tokens
    //a este le pasamos las llaves que tenemos para que desencripte el token, vea que tiene dentro y verifique si es correcto
    public DecodedJWT validateToken(String token) {
        try {
            //obtener el algoritmo con el cual esto fue encriptado
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            //requiere el algoritmo para hacer la verificacion
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            //si todo esta ok, no genera ninguna excepcion y nos devuelve el JWT decodificado
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Invalid token, Not authorized");
        }
    }

    //metodo para obtener el usuario/username
    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    //metodo para obtener un claim en particular
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    //metodo para obtener los claims
    public Map<String,Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
