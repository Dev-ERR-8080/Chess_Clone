package org.example.chess_clone.config;

import org.example.chess_clone.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userService;
    private final JwtAuthenticationFilter jwtFilter;
    public SecurityConfig(UserDetailsService userService, JwtAuthenticationFilter jwtFilter) {
        this.userService = userService;
        this.jwtFilter=jwtFilter;
    }

    @Bean
    public SecurityFilterChain newSecurityFilterChain(HttpSecurity http, DefaultAuthenticationEventPublisher authenticationEventPublisher) throws Exception {

            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session->session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests(auth->auth
                            .requestMatchers("/auth/login","/auth/register","/user/hello").permitAll()
                            .anyRequest().authenticated()
                    )
                    .authenticationProvider(authenticationProvider())
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
    }

    // 🔐 Password encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // Use BCryptPasswordEncoder for password hashing
        return new BCryptPasswordEncoder();
    }

    // 🔑 Authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    // 🔁 Authentication manager (uses provider automatically)
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Set the UserDetailsService and password encoder
        authenticationManagerBuilder
                .userDetailsService(userService);


        // Return the AuthenticationManager object
        return authenticationManagerBuilder.build();
    }
}
