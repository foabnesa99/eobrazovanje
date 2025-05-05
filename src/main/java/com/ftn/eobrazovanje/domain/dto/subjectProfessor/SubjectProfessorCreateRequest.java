package com.ftn.eobrazovanje.domain.dto.subjectProfessor;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectProfessorCreateRequest {

    private Long subjectId;

    private Long professorId;

    private ProfessorRole professorRole;

}
