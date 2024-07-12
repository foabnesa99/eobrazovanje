package com.ftn.eobrazovanje.domain.entity.relational;

import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
public class SubjectStudentAttendance {

    @EmbeddedId
    protected SubjectStudentAttendanceId id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @MapsId("subjectId")
    private Subject subject;

    private int finalGrade;

    private boolean passed;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectStudentAttendance")
    private List<SubjectStudentTest> subjectStudentTests = new ArrayList<>();

    public SubjectStudentAttendance(Student student, Subject subject) {
        this.student = student;
        this.subject = subject;
        this.id = new SubjectStudentAttendanceId(subject.getId(), student.getId());
    }

    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SubjectStudentAttendanceId implements Serializable {

        private Long subjectId;
        private Long studentId;

    }


}
