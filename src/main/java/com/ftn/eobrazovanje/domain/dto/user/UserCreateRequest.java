package com.ftn.eobrazovanje.domain.dto.user;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import com.ftn.eobrazovanje.domain.common.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateRequest {

    @Email
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String phone;

    private UserRole role = UserRole.STUDENT;

    private ProfessorRole professorRole;

}
