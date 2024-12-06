package com.todocodeacademy.springsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//por defecto spring security cubre todos los endpoints
//el usuario siempre es user y la constraseña es la contraseña por defecto
@RestController
//como actuara la seguridad en cada uno de los endpoints
@PreAuthorize("denyAll()") //"permitAll()"
public class HelloWorldController {

    @GetMapping("/holaseg")
    //dandole la seguirdad directamente en el end point
    @PreAuthorize("hasAuthority('READ')")
    public String secHelloWorld(){
        return "Hola mundo TodoCode con seguirdad";
    }

    @GetMapping("/holanoseg")
    //permite a todos entrar a este endpoint
    @PreAuthorize("permitAll()")
    public String noSecHelloWolrd(){
        return "Hola munso Todocode sin seguirda";
    }
}
