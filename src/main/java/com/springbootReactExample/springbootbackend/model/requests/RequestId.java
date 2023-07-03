package com.springbootReactExample.springbootbackend.model.requests;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class RequestId implements Serializable {
    private String userId;
    private String localDateTime;

    public RequestId(String userId, String localDateTime) {
        this.userId = userId;
        this.localDateTime = localDateTime;
    }

    public RequestId() {
    }
}
