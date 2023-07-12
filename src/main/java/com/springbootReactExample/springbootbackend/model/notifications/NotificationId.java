package com.springbootReactExample.springbootbackend.model.notifications;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class NotificationId implements Serializable {
    private String userId;
    private String localDateTime;

    public NotificationId(String userId, String localDateTime){
        this.userId = userId;
        this.localDateTime = localDateTime;
    }

}
