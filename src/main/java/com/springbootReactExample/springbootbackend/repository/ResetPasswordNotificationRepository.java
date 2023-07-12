package com.springbootReactExample.springbootbackend.repository;

import com.springbootReactExample.springbootbackend.model.notifications.NotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springbootReactExample.springbootbackend.model.notifications.ResetPasswordNotification;

import java.util.List;

public interface ResetPasswordNotificationRepository extends JpaRepository<ResetPasswordNotification, NotificationId> {
    List<ResetPasswordNotification> findAllByNotificationIdUserId(String userId);
}
