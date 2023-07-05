package com.springbootReactExample.springbootbackend.repository;

import com.springbootReactExample.springbootbackend.model.requests.RequestId;
import com.springbootReactExample.springbootbackend.model.requests.ResetPasswordRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResetPasswordRequestRepository extends JpaRepository<ResetPasswordRequest, RequestId> {
    //DONE: findByUserId and findByLocalDateTime
    List<ResetPasswordRequest> findAllByRequestIdUserId(String userId);
    List<ResetPasswordRequest> findAllByRequestIdLocalDateTime(String localDateTime);

}
