package com.springbootReactExample.springbootbackend.service;

import com.springbootReactExample.springbootbackend.model.requests.Request;
import com.springbootReactExample.springbootbackend.repository.ResetPasswordRequestRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {
    private static final Logger LOGGER = LogManager.getLogger(RequestService.class);
    @Autowired
    private final ResetPasswordRequestRepository resetPasswordRequestRepository;

    @SuppressWarnings("unchecked")
    public <T extends Request>List<T> getRequests(){
        return (List<T>) resetPasswordRequestRepository.findAll();
    }

    @SuppressWarnings("unchecked")
    public <T extends Request> List<T> getUserRequests(String id) {
        return (List<T>) resetPasswordRequestRepository.findAllByRequestIdUserId(id);
    }
}
