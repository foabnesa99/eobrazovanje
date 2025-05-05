package com.ftn.eobrazovanje.domain.entity.relational;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.domain.entity.user.Professor;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
public class SubjectProfessor {

    @EmbeddedId
    protected SubjectProfessorId id;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @MapsId("professorId")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @MapsId("subjectId")
    private Subject subject;

    @Enumerated(EnumType.STRING)
    private ProfessorRole professorRole;

    public SubjectProfessor(Professor professor, Subject subject, ProfessorRole professorRole) {
        this.professor = professor;
        this.subject = subject;
        this.professorRole = professorRole;
        this.id = new SubjectProfessorId(subject.getId(), professor.getId());
    }

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SubjectProfessorId implements Serializable {

        private Long subjectId;
        private Long professorId;

    }


}
