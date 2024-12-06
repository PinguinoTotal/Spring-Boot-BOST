package com.todocodeacademy.springsecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

//le decimos al programa que vamos a haecr una configuracion
@Configuration
//le decimos que esa configuracion es de security
@EnableWebSecurity
//permite la seguidad por metodo
@EnableMethodSecurity
public class SecurityConfig {

    //security hace que intercepte todas las peticiones http, tanto las que entran como las que pueden llear a salir
    //y aplica logica de seguridad
    @Bean //el bean nos indica que se va acomportar como un objetod entro de springBoot
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

                //esta es una modificacion del codigo suandop la seguirdad en cada uno de los endpoints
                //csrf puede ser un ataque
                .csrf(csrf -> csrf.disable())
                //incluyendo las contraseñas en la cabecera de la request (no es la mejor practica)
                //esto se usa cuando vamos a hacer logins con usuarios y contraseñas
                .httpBasic(Customizer.withDefaults())
                //hacemos que las sessiones sean satateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //establece que se va a autorizar y no del programa
                .build();


                /*
                //csrf puede ser un ataque
                .csrf(csrf -> csrf.disable())
                //incluyendo las contraseñas en la cabecera de la request (no es la mejor practica)
                //esto se usa cuando vamos a hacer logins con usuarios y contraseñas
                .httpBasic(Customizer.withDefaults())
                //hacemos que las sessiones sean satateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //establece que se va a autorizar y no del programa
                .authorizeHttpRequests(http -> {
                    //Endpoints
                    http.requestMatchers(HttpMethod.GET, "holanoseg").permitAll();
                    //solo pueden usar este endpoint los que tienen la autoridad de lectura
                    http.requestMatchers(HttpMethod.GET, "holaseg").hasAuthority("READ");
                    http.anyRequest().denyAll();
                })
                .build();
                */



                /*
                //establece que se va a autorizar y no del programa
                .authorizeHttpRequests()
                //define una regla de autorizacion, una ruta, el endpoint, la solicitud que matche
                .requestMatchers("/holanoseg")
                //permite el acceso de todas las solicitudes, lo hace no segurizado
                .permitAll()
                //toda solicitud que no coincida con lo anterior
                .anyRequest()
                //va tener que ser autenticado
                .authenticated()
                .and()
                //el formato del login
                .formLogin()
                //muestraselosa todos, o todos pueden acceder a su ruta
                .permitAll()
                //incluyendo las contraseñas en la cabecera de la request (no es la mejor practica)
                .and().httpBasic()

                .and()
                //terminando la configuracion y construye la cadena de filtros
                .build();
                */
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        //de la configuracion de autentificacion esta clase regresa un AuthenticationManager
        return authenticationConfiguration.getAuthenticationManager();
    }

    /* Esta es la primer iteracion del codigo de abajo
    @Bean
    //un proveedor de autentificacion
    public AuthenticationProvider authenticationProvider(){
        //data acces object
        //este nos deja ver el usuario y el encriptador de contraseñas
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
     */

    @Bean
    //un proveedor de autentificacion
    //no tenemos que construir esto con nada ya que por default, Sping roma nuestro UserDetailsServiceImp como
    //la instancia que lo configurara
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    //retorna el algoritmo con el cual vamos a encriptar nuestras contraseñas
    public PasswordEncoder passwordEncoder(){
        //encripatando las contraseñas
        return new  BCryptPasswordEncoder();
    }

    /*
    esto es cuando dejabamos el texto plano como password
    @Bean
    //retorna el algoritmo con el cual vamos a encriptar nuestras contraseñas
    public PasswordEncoder passwordEncoder(){
        //por ahora como son pruebas no usamos ninguna encriptacion
        return NoOpPasswordEncoder.getInstance(); //no hacer esto en produccion
    }

     */

    /* ya no hacemos uso de este porque definicmos en servicicos, como tomaremos los usuarios de la base de datos
    @Bean
    //el user detail service es la parte compleja de implementar, este es un servicio
    public UserDetailsService userDetailsService(){
        //esto es solo a nivel logico, depues se tiene que tener una base de datos
        List userDetailsList = new ArrayList<>();

        userDetailsList.add(User.withUsername("todocode")
                .password("1234")
                .roles("ADMIN")
                .authorities("CREATE","READ","UPDATE","DELETE")
                .build());

        userDetailsList.add(User.withUsername("seguidor")
                .password("1234")
                    .roles("USER")
                .authorities("READ")
                .build());

        userDetailsList.add(User.withUsername("actualizador")
                .password("1234")
                .roles("USER")
                .authorities("UPDATE")
                .build());

        //retoranmos nuestra lista de Usuarios con sus permisos
        return  new InMemoryUserDetailsManager(userDetailsList);
    }

     */
}




