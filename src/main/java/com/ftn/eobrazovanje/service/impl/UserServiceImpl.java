package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.UserRepository;
import com.ftn.eobrazovanje.domain.common.UserRole;
import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.domain.dto.user.UserMapper;
import com.ftn.eobrazovanje.domain.entity.user.Professor;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.service.ProfessorService;
import com.ftn.eobrazovanje.service.StudentService;
import com.ftn.eobrazovanje.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final StudentService studentService;
    private final ProfessorService professorService;
    private final UserRepository userRepository;

    @Override
    public void createUser(UserCreateRequest userCreateRequest) {
        if(userCreateRequest.getRole().equals(UserRole.STUDENT)){
            Student student = UserMapper.INSTANCE.studentFromUserCreateRequest(userCreateRequest);
            studentService.create(student);
        }

            createProfessor(userCreateRequest);
    }

    private void createProfessor(UserCreateRequest userCreateRequest) {
        Professor professor = UserMapper.INSTANCE.professorFromUserCreateRequest(userCreateRequest);
        professorService.create(professor);

    }
}
