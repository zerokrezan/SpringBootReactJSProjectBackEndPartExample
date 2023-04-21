package com.springbootReactExample.springbootbackend.config;

import com.springbootReactExample.springbootbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private final UserService myUserDetailsService;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/posts/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin()
                    //.loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and()
                .userDetailsService(myUserDetailsService)
                .headers()
                    .frameOptions().sameOrigin()
                    .and()
                .httpBasic(withDefaults())
                .logout() // add logout configuration
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                .build();
        /*return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/h2-console/**", "/login").permitAll()
                        //.requestMatchers("/api/posts/**").permitAll()
                        .anyRequest().authenticated()
                        )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                        .usernameParameter("email")
                        .passwordParameter("password")
                )
                .userDetailsService(myUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(withDefaults())
                //.formLogin(Customizer.withDefaults())
                .build();*/

        /*return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/posts/**").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(myUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(withDefaults())
                .build();*/
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }
}
