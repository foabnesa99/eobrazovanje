package com.ftn.eobrazovanje.domain.dto.studyProgramSubject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyProgramSubjectCreateRequest {

    private Long studyProgramId;
    private Long subjectId;

}
