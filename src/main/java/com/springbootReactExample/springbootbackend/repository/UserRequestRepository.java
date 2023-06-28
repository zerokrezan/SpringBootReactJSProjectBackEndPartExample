package com.springbootReactExample.springbootbackend.repository;

import com.springbootReactExample.springbootbackend.model.requests.RequestId;
import com.springbootReactExample.springbootbackend.model.requests.ResetPasswordRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRequestRepository extends JpaRepository<ResetPasswordRequest, RequestId> {
    //TODO: findByUserId and findByLocalDateTime

}
