package com.ftn.eobrazovanje.domain.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long studyProgramId;

}
