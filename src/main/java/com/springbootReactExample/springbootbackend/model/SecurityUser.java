package com.springbootReactExample.springbootbackend.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class SecurityUser implements UserDetails {
    private static final Logger LOGGER = LogManager.getLogger(SecurityUser.class);
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
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<? extends GrantedAuthority> list;
        LOGGER.info("check authoritiy of user: "+ user.getId());
        if (user.isAdminRights()){
            LOGGER.info("user with "+user.getId()+" has ROLE_ADMIN");
            list = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            LOGGER.info("user who is just logged in has authority as admin");
            return list;

        }
        LOGGER.info("user with "+user.getId()+" has ROLE_USER");
        list = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        LOGGER.info("user who ist just logged in has authority as user");
        return list;
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
