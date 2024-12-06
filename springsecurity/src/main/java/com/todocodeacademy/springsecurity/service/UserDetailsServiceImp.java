package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.UserSec;
import com.todocodeacademy.springsecurity.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//ectiende de la clase UserDetailsService que ya tiene sus metodos seguros, ya que esta deciende de
//spring security
public class UserDetailsServiceImp implements UserDetailsService {

    //necesitamos acceder a las base de datos de los usuarios por eso inyectamos la dependencia de userRepository
    @Autowired
    private IUserRepository userRepo;

    @Override
    //este recibe un nombre de usuario
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //esta en formato UserSec, nosotros necesitamos retorar en UserDetails
        //esto puede devolver un null
        //1 trater nuesto usuario de la base de datos
        //2 tener la lista de permisos, autoritylist
        UserSec userSec = userRepo.findUserSecByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("El usuario " + username  + "no fue encontrado"));

        //crear una lista auxiliar que gestionara los permisos
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //lo que hace este codigo es traer los roles y convertirlos en SimpleGrantedAuthority
        userSec.getRolelist()
                //con esto hacemos la diferencia de los permisos a los roles, con el "ROLE_" para que no se confundan
                //no como los permisos que fueron guardadps a secas en el codigo de abajo
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        //lo que hace este codigo es traer los permisos y convertirlos en SimpleGrantedAuthority
        //traer los roles y convertirlos en simpleGrantedAuthority
        //stream es una secuencia de objetos, transformamos la rolelist en un stream para poder usar sus metodos
        userSec.getRolelist().stream()
                //flatmap nos sirve para hacer un mapeo que tiene los permissions de rol
                .flatMap(role -> role.getPermissionsList().stream())
                        .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));


        //este user es el user que utiliza SpringSecurity
        return  new User(
                userSec.getUserName(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isAccountNotExpired(),
                userSec.isAccountNotLocked(),
                authorityList);
    }
}
