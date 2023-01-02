package com.example.apispring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.apispring.security.JWTAuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Configuracion para excluir endpoints
     *
     * @param http
     * @throw Exception
     */
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf()
                .disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(
                        "/students",
                        "/courses"
                )
                .hasAnyAuthority("ADMIN")
                .antMatchers("/students")
                .hasAnyAuthority("OPERATOR")
                .antMatchers("/login")
                .permitAll();
    }

    /**
     * configuracion para excluir paginas
     *
     * @param web
     * @throws "Exception"
     */
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring()
                .antMatchers("/logout");
    }
}
