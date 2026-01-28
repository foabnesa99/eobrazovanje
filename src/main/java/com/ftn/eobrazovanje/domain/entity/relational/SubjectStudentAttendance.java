package com.ftn.eobrazovanje.domain.entity.relational;

import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
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
public class SubjectStudentAttendance extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    private int finalGrade;

    private boolean passed;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectStudentAttendance")
    private List<SubjectStudentTest> subjectStudentTests = new ArrayList<>();


}
