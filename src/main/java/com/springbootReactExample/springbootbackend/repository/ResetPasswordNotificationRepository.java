package com.springbootReactExample.springbootbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springbootReactExample.springbootbackend.model.notifications.ResetPasswordNotification;
public interface ResetPasswordNotification extends JpaRepository<com.springbootReactExample.springbootbackend.model.notifications.ResetPasswordNotification> {
}
