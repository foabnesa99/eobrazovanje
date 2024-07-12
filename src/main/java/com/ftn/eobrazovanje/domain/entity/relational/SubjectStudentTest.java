package com.ftn.eobrazovanje.domain.entity.relational;

import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.FetchType;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import java.time.LocalDateTime;

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

    private int grade;

    private boolean attended;

    private LocalDateTime dateTime;




}
