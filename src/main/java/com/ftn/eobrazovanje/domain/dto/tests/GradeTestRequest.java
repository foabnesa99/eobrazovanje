package com.ftn.eobrazovanje.domain.dto.tests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GradeTestRequest {
    private Long subjectStudentTestId;
    private int grade;
    private int points;
    private boolean passed;
}
