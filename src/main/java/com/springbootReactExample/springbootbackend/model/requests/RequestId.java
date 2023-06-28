package com.springbootReactExample.springbootbackend.model.requests;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class RequestId implements Serializable {
    private String userId;
    private LocalDateTime localDateTime;

    public RequestId(String userId, LocalDateTime localDateTime) {
        this.userId = userId;
        this.localDateTime = localDateTime;
    }

    public RequestId() {
    }
}
