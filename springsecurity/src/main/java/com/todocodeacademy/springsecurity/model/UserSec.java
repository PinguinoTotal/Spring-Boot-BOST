package com.todocodeacademy.springsecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
//el nombre de la tabla en la base de datos sera users, el nombre de las clases debe estar en
//singular mientras que las tablas deben estar en plural
@Table(name = "users")
public class UserSec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //el usuario tiene que ser unico
    @Column(unique = true)
    private String userName;
    private String password;
    //estos parametros ya vienen por default
    //usuario dadod e baja, esto porque no es una buena practica eliminar los registros de la base de datos
    private boolean enabled;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialNotExpired;

    //conexion unidireccional, usersec, conoce a role, pero role no conoce a user sec
    //usamos set porque no permite repetidos, un usuario puede tener diferentes roles, pero no tiene
    //sentido que los roles se repitan como admin dos veces
    //list permite repetidos
    //fetch es el tipo de carga que van a tener los datos
    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL) //el eanger me va a cargar todos los roles
    //tabla intermedia que une a los datos n a n
    //join columns, como se va a llamar la columna de la tabla que unira las dos tablas
    //este es el forenkey de los dos, osease primary key de users y de Roles
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> rolelist = new HashSet<>();

    public UserSec(Long id, String userName, String password, boolean enabled, boolean accountNotExpired, boolean accountNotLocked, boolean credentialNotExpired, Set<Role> rolelist) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.accountNotExpired = accountNotExpired;
        this.accountNotLocked = accountNotLocked;
        this.credentialNotExpired = credentialNotExpired;
        this.rolelist = rolelist;
    }

    public UserSec() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNotExpired() {
        return accountNotExpired;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public boolean isAccountNotLocked() {
        return accountNotLocked;
    }

    public void setAccountNotLocked(boolean accountNotLocked) {
        this.accountNotLocked = accountNotLocked;
    }

    public boolean isCredentialNotExpired() {
        return credentialNotExpired;
    }

    public void setCredentialNotExpired(boolean credentialNotExpired) {
        this.credentialNotExpired = credentialNotExpired;
    }

    public Set<Role> getRolelist() {
        return rolelist;
    }

    public void setRolelist(Set<Role> rolelist) {
        this.rolelist = rolelist;
    }

    @Override
    public String toString() {
        return "UserSec{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", accountNotExpired=" + accountNotExpired +
                ", accountNotLocked=" + accountNotLocked +
                ", credentialNotExpired=" + credentialNotExpired +
                ", rolelist=" + rolelist +
                '}';
    }
}
