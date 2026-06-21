package com.ftn.eobrazovanje.domain.dto.professor;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private ProfessorRole professorRole;

}
