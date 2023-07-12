package com.springbootReactExample.springbootbackend.model.notifications;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordNotification extends Notification {
    @Override
    @EmbeddedId
    public NotificationId getNotificationId() {
        return super.getNotificationId();
    }

    public ResetPasswordNotification(NotificationId notificationId, String message){
        this.notificationId = notificationId;
        this.message = message;
    }
}
