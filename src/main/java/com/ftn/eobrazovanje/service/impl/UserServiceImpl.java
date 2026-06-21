package com.ftn.eobrazovanje.service.impl;

import com.ftn.eobrazovanje.dao.UserRepository;
import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import com.ftn.eobrazovanje.domain.common.UserRole;
import com.ftn.eobrazovanje.domain.dto.professor.ProfessorUpdateRequest;
import com.ftn.eobrazovanje.domain.dto.security.CustomUserDetails;
import com.ftn.eobrazovanje.domain.dto.student.StudentUpdateRequest;
import com.ftn.eobrazovanje.domain.dto.user.UserCreateRequest;
import com.ftn.eobrazovanje.domain.dto.user.UserDto;
import com.ftn.eobrazovanje.domain.entity.StudentAccount;
import com.ftn.eobrazovanje.domain.entity.user.Professor;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.domain.entity.user.User;
import com.ftn.eobrazovanje.exception.UserNotFoundException;
import com.ftn.eobrazovanje.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final StudentService studentService;
    private final ProfessorService professorService;
    private final StudyProgramService studyProgramService;
    private final UserRepository userRepository;
    private final StudentAccountService studentAccountService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        user.setEmail(userCreateRequest.getEmail());
        user.setFirstName(userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName());
        user.setRegistrationDate(LocalDate.now());
        user.setPhone(userCreateRequest.getPhone());
        user.setPassword(passwordEncoder.encode("password"));

        if(userCreateRequest.getRole().equals(UserRole.STUDENT)){
            user.setRole(UserRole.STUDENT);
            create(user);
            Student student = new Student();
            student.setUser(user);
            StudentAccount studentAccount = new StudentAccount();
            studentAccount.setStudent(student);
            studentAccount.setBalance(0L);
            studentService.create(student);
            studentAccountService.create(studentAccount);
            studyProgramService.addStudentToStudyProgram(userCreateRequest.getStudyProgramId(), student);
        }
        else{
            createProfessor(user, userCreateRequest.getProfessorRole());
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserDto> findAllDtos() {
        return userRepository.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phone(user.getPhone())
                        .role(user.getRole())
                        .build())
                .toList();
    }

    private void createProfessor(User user, ProfessorRole professorRole) {
        user.setRole(UserRole.PROFESSOR);
        Professor professor = new Professor(user);
        professorService.create(professor);

    }

    @Override
    public User fetchCurrentUser(){
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getByEmail(customUserDetails.getUsername());
    }

    @Override
    public void updateStudent(Long studentId, StudentUpdateRequest request) {
        Student student = studentService.findById(studentId);
        User user = student.getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        studentService.update(student);
        studyProgramService.updateStudentProgram(request.getStudyProgramId(), student);
    }

    @Override
    public void updateProfessor(Long professorId, ProfessorUpdateRequest request) {
        Professor professor = professorService.getById(professorId);
        User user = professor.getUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        professorService.update(professor);
    }
}
