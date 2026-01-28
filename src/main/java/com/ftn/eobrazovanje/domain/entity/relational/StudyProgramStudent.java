package com.ftn.eobrazovanje.domain.entity.relational;

import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
public class StudyProgramStudent {

    @EmbeddedId
    protected StudyProgramStudent.StudyProgramStudentId id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "study_program_id")
    @MapsId("studyProgramId")
    private StudyProgram studyProgram;

    @Builder.Default
    private int programYear = 1;

    public StudyProgramStudent(Student student, StudyProgram studyProgram) {
        this.student = student;
        this.studyProgram = studyProgram;
        this.programYear = 1;
        this.id = new StudyProgramStudent.StudyProgramStudentId(studyProgram.getId(), student.getId());
    }

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class StudyProgramStudentId implements Serializable {

        private Long studyProgramId;
        private Long studentId;

    }

}
