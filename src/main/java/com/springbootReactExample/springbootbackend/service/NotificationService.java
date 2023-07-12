package com.springbootReactExample.springbootbackend.service;

import com.springbootReactExample.springbootbackend.model.notifications.Notification;
import com.springbootReactExample.springbootbackend.repository.ResetPasswordNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final Logger LOGGER = LogManager.getLogger(NotificationService.class);
    private final ResetPasswordNotificationRepository resetPasswordNotificationRepository;

    @SuppressWarnings("unchecked")
    public <T extends Notification> List<T> getNotifications(String id) {
        return (List<T>) resetPasswordNotificationRepository.findAllByNotificationIdUserId(id);
    }
}
