package com.todocodeacademy.springsecurity.controller;

import com.todocodeacademy.springsecurity.model.Role;
import com.todocodeacademy.springsecurity.model.UserSec;
import com.todocodeacademy.springsecurity.service.IRoleService;
import com.todocodeacademy.springsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;


    @GetMapping
    public ResponseEntity<List<UserSec>> getAllUsers(){
        List<UserSec> userSecList = userService.findAll();
        return ResponseEntity.ok(userSecList);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserSec> getUserById(@PathVariable Long id){
        Optional<UserSec> userSec = userService.findById(id);
        return userSec.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec){
        Set<Role> roleList = new HashSet<>();
        Role readRole;

        //encriptando la contraseña
        userSec.setPassword(userService.encriptPassword(userSec.getPassword()));

        //Recuperar la Permission/s por su ID
        for (Role role: userSec.getRolelist()){
            readRole =roleService.findById(role.getId()).orElse(null);
            if (readRole !=null){
                //si encuentro, guardo en la lista
                roleList.add(readRole);
            }
        }

        if (!roleList.isEmpty()){
            userSec.setRolelist(roleList);

            UserSec newUser = userService.save(userSec);
            return  ResponseEntity.ok(newUser);
        }
        return null;
    }
}
