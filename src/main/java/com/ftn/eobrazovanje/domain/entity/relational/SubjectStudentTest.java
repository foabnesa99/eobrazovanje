package com.ftn.eobrazovanje.domain.entity.relational;

import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
public class SubjectStudentTest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_student_attendance_id")
    private SubjectStudentAttendance subjectStudentAttendance;

    @ManyToOne
    @JoinColumn(name = "subject_test_id")
    private SubjectTest subjectTest;

    private int grade;

    private int points;

    private boolean passed;

    private boolean attended;


}
