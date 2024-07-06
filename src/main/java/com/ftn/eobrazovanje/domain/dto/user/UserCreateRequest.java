package com.ftn.eobrazovanje.domain.dto.user;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import com.ftn.eobrazovanje.domain.common.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateRequest {

    private String email;

    private String firstName;

    private String lastName;

    private String phone;

    private UserRole role = UserRole.STUDENT;

    private ProfessorRole professorRole;

}
