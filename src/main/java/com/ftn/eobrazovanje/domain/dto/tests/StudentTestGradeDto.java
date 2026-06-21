package com.ftn.eobrazovanje.domain.dto.tests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentTestGradeDto {

    private Long id;
    private String studentName;
    private int grade;
    private int points;
    private boolean passed;
    private boolean attended;

}
