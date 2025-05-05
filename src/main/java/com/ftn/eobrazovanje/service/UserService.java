package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.domain.dto.user.UserSimpleDto;
import com.ftn.eobrazovanje.domain.entity.user.User;

import java.util.List;

public interface UserService{

    User createUser(UserCreateRequest userCreateRequest);

    User getByEmail(String email);

    User create(User user);
    List<User> findAll();

    List<User> findAllProfessors();
}
