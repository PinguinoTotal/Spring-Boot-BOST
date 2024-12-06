package com.todocodeacademy.springsecurity.repository;

import com.todocodeacademy.springsecurity.model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserSec, Long> {

    //consulta personalizada
    //jpa detecta lo que queremos buscar por que lo decimos en ingles
    Optional<UserSec> findUserSecByUserName(String username);
}
