package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.domain.entity.user.User;

public interface UserService{

    void createUser(UserCreateRequest userCreateRequest);

    User getByEmail(String email);
    
}
