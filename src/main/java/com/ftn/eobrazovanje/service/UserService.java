package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;

public interface UserService{

    void createUser(UserCreateRequest userCreateRequest);
    
}
