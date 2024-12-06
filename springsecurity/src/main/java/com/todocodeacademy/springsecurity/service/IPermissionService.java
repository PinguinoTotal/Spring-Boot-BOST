package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.Permission;

import java.util.List;
import java.util.Optional;

public interface IPermissionService {
    List<Permission> findAll();
    //optional nos sirve para trabajar con los nulls
    Optional<Permission> findById(Long id);
    Permission save(Permission permission);
    void deleteById(Long id);
    Permission update(Permission permission);
}
