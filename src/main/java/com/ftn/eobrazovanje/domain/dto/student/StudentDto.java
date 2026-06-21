package com.ftn.eobrazovanje.domain.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long studyProgramId;

}
