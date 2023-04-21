package com.springbootReactExample.springbootbackend.model;

import com.springbootReactExample.springbootbackend.controller.UserController;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
public class SecurityUser implements UserDetails {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        LOGGER.info("check authoritiy of user: "+ user.getEmail());
        if (user.isAdminRights()){
            LOGGER.info("user with "+user.getEmail()+" has ROLE_ADMIN");
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));

        }
        LOGGER.info("user with "+user.getEmail()+" has ROLE_USER");
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
