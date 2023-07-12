package com.springbootReactExample.springbootbackend.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Request {

    public RequestId requestId;
    public Request() {
    }
}
