package com.ftn.eobrazovanje.domain.dto.attendance;

import com.ftn.eobrazovanje.domain.dto.tests.SubjectStudentTestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectAttendanceDto {
    private Long id;
    private String subjectTitle;
    private int finalGrade;
    private boolean passed;
    private List<SubjectStudentTestDto> tests;
}
