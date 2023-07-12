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
    @Override
    @EmbeddedId
    public RequestId getRequestId() {
        return super.getRequestId();
    }

    private String newPassword;
    public ResetPasswordRequest(RequestId requestId, String newPassword) {
        this.requestId = requestId;
        this.newPassword = newPassword;
    }
    public ResetPasswordRequest() {
    }
}
