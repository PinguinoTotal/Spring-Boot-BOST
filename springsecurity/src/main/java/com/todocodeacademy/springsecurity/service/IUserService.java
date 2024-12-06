package com.todocodeacademy.springsecurity.service;

import com.todocodeacademy.springsecurity.model.Role;
import com.todocodeacademy.springsecurity.model.UserSec;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserSec> findAll();
    Optional<UserSec> findById(Long id);
    UserSec save(UserSec usersec);
    void deleteById(Long id);
    void update(UserSec usersec);

    String encriptPassword(String password);
}
