package com.ftn.eobrazovanje.domain.dto.tests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectStudentTestDto {
    private Long id;
    private String testTitle;
    private int grade;
    private int points;
    private boolean passed;
    private boolean attended;
}
