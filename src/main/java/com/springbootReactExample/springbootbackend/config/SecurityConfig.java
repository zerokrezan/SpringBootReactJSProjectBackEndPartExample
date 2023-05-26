
package com.springbootReactExample.springbootbackend.config;

import com.springbootReactExample.springbootbackend.controller.UserController;
import com.springbootReactExample.springbootbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private final UserService myUserDetailsService;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/login"))
                //.csrf(c->c.ignoringRequestMatchers("/login"))
                .cors()
                .and()
                .authorizeRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        //.requestMatchers("/api/posts/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin()
                    //.loginPage("http://localhost:3000/")
                    .permitAll()
                    //.defaultSuccessUrl("/logout", true)
                    //.defaultSuccessUrl("http://localhost:3000/users", true)
                    .failureUrl("/authentificationFailed")
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                .userDetailsService(myUserDetailsService)
                .headers()
                    .frameOptions().sameOrigin()
                    .and()
                .httpBasic(withDefaults())
                .logout() // add logout configuration
                    //.logoutUrl("/logout")
                    //.logoutSuccessHandler(logoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .and()
                .httpBasic(withDefaults());

                return http.build();







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
                .build();*//*



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

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Bean
    LogoutSuccessHandler logoutSuccessHandler(){
        return (request, response, authentication) -> {
            LOGGER.info("logoutsuccesshandler");
            // Perform any desired logging or additional actions here
            if (authentication != null) {
                LOGGER.info("authentication "+ authentication);
                String username = authentication.getName();
                System.out.println("User logged out: " + username);
            }
            //response.sendRedirect("/login"); // Redirect to the login page after logout
        };
    }
}

