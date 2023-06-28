package com.springbootReactExample.springbootbackend.model.requests;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "resetpasswordrequests")
public class ResetPasswordRequest extends Request {

    @EmbeddedId
    private RequestId requestId;
    public ResetPasswordRequest(RequestId requestId) {
        this.requestId = requestId;
    }
    public ResetPasswordRequest() {
    }
}
