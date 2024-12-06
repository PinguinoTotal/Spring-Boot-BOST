package com.todocodeacademy.springsecurity.controller;

import com.todocodeacademy.springsecurity.model.Permission;
import com.todocodeacademy.springsecurity.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    IPermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions(){
        List<Permission> permissions = permissionService.findAll();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable  Long id) {
        Optional<Permission> permission = permissionService.findById(id);
        //el metodo map se utiliza para hacer una transformacion del valor de lo que se tiene en parentesis
        //a un optional si es lo que hubiere
        //el map le da un formato
        //or else get es de optional, usar un valor determinado si nuestro optinal esta vacio
        return permission.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Permission> createPermision(@RequestBody Permission permission){
        Permission newPermision = permissionService.save(permission);
        return ResponseEntity.ok(newPermision);
    }
}
