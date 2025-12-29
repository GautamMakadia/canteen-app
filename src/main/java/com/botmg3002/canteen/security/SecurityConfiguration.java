package com.botmg3002.canteen.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.botmg3002.canteen.model.UserRole;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private UserFilter userFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request.requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/customer/**").hasRole(UserRole.CUSTOMER.toString())
                        .requestMatchers("/admin/**").hasRole(UserRole.ADMIN.toString())
                        .requestMatchers("/order/**").hasAnyRole(UserRole.ADMIN.toString(), UserRole.CUSTOMER.toString())
                        .requestMatchers("/image/**").hasAnyRole(UserRole.ADMIN.toString(), UserRole.CUSTOMER.toString())
                        .requestMatchers("/cart/**").hasRole(UserRole.CUSTOMER.toString())
                        .requestMatchers("/category/**").authenticated()
                        .anyRequest().authenticated())
                .addFilterBefore(userFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider);
    }
}
