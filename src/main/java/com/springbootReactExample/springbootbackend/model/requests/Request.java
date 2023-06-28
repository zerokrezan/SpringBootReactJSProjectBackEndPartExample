package com.springbootReactExample.springbootbackend.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Request {

    private RequestId requestId;
    public Request() {
    }
}
