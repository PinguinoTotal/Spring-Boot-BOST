package com.todocodeacademy.springsecurity.controller;

import com.todocodeacademy.springsecurity.model.Permission;
import com.todocodeacademy.springsecurity.model.Role;
import com.todocodeacademy.springsecurity.service.IPermissionService;
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
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    //tenemos que inyectar otro servicio, porque tenemos que recuperar los permisos
    //cuando se crea un rol nuevo, debe acceder a sus datos
    @Autowired
    private IPermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id){
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    //debemos buscar los permisos que trae el request body
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        //tenemos una "lista" de permisions
        Set<Permission> permiList = new HashSet<>();
        //variable auxiliar que nos ayudara a leer los permisos
        Permission readPermision;

        //Recuperar la Permission/s por su ID
        //va a leer todos los permissions que trae el role
        for (Permission per: role.getPermissionsList()){
            //busca en la base de datos si lo encuentra lo guarda, si no devuelve un null
            readPermision = permissionService.findById(per.getId()).orElse(null);
            if (readPermision != null){
                //si encuentro guardo en la lista
                permiList.add(readPermision);
            }
        }

        //le ponemos al rol la lista de permison que ya construimos
        role.setPermissionsList(permiList);
        //creamos un nuevo rol que nos llego
        //y devolvemos la referencia que arrojamos al final del codigo
        Role newRole = roleService.save(role);
        return ResponseEntity.ok(newRole);

    }
}
