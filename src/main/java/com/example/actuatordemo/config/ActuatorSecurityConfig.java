package com.example.actuatordemo.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ActuatorSecurityConfig extends WebSecurityConfigurerAdapter {

     /*
        Esta configuración de seguridad de Spring hace lo siguiente ...

        1. Restringe el acceso al endpoint de apagado al rol ACTUATOR_ADMIN.
        2. Permite el acceso a todos los demás endpoint del actuator.
        3. Permite el acceso a recursos estáticos.
        4. Permite el acceso a la página de inicio (/).
        5. Todas las demás solicitudes deberan estar autenticadas.
        5. Habilita la autenticación básica http para completar la configuración.
           Puede utilizar cualquier otra forma de autenticación.
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .requestMatchers(EndpointRequest.to(ShutdownEndpoint.class))
                        .hasRole("ACTUATOR_ADMIN")
                    .requestMatchers(EndpointRequest.toAnyEndpoint())
                        .permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                    .antMatchers("/", "/slowApi")
                        .permitAll()
                    .antMatchers("/**")
                        .authenticated()
                .and()
                .httpBasic();
    }
}
