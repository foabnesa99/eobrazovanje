package com.ftn.eobrazovanje.service;

import com.ftn.eobrazovanje.domain.dto.professor.ProfessorUpdateRequest;
import com.ftn.eobrazovanje.domain.dto.student.StudentUpdateRequest;
import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.domain.dto.user.UserDto;
import com.ftn.eobrazovanje.domain.entity.user.User;

import java.util.List;

public interface UserService{

    User createUser(UserCreateRequest userCreateRequest);

    User getByEmail(String email);

    User create(User user);

    User fetchCurrentUser();

    List<UserDto> findAllDtos();

    void updateStudent(Long studentId, StudentUpdateRequest request);

    void updateProfessor(Long professorId, ProfessorUpdateRequest request);
}
