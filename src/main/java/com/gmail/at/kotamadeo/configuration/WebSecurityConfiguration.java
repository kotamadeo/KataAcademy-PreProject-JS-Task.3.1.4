package com.gmail.at.kotamadeo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    private final AuthenticationSuccessUserHandler authenticationSuccessUserHandler;
    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfiguration(AuthenticationSuccessUserHandler authenticationSuccessUserHandler,
                                    UserDetailsService userDetailsService) {
        this.authenticationSuccessUserHandler = authenticationSuccessUserHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login/**").permitAll()
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/login").permitAll().successHandler(authenticationSuccessUserHandler)
                .and()
                .logout()
                .permitAll()
                .and()
                .httpBasic();
        return http.build();
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}