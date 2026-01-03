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
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userService, JwtAuthenticationFilter jwtFilter, OAuth2SuccessHandler oAuth2SuccessHandler, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtFilter=jwtFilter;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.passwordEncoder=passwordEncoder;
    }

    @Bean
    public SecurityFilterChain newSecurityFilterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(session->session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    )
                    .authorizeHttpRequests(auth->auth
                            .requestMatchers(
                                    "/auth/**",
                                    "/oauth2/**",
                                    "/OAuthLogin.html"
                            )
                            .permitAll()
                            .anyRequest().authenticated()
                    )

                    .oauth2Login(oauth2->oauth2
                            .successHandler(oAuth2SuccessHandler)
                            .authorizationEndpoint(auth -> auth.baseUri("/oauth2/authorization"))
                    )
                    .authenticationProvider(authenticationProvider())
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userService).passwordEncoder(passwordEncoder);


        // Return the AuthenticationManager object
        return authenticationManagerBuilder.build();
    }
}
