package com.springbootReactExample.springbootbackend.model.notifications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class Notification {
    public NotificationId notificationId;
    public String message;
}
