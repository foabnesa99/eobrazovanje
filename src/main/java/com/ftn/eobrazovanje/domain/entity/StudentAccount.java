package com.ftn.eobrazovanje.domain.entity;


import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
public class StudentAccount extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Student student;

    private Long balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "studentAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentAccountTransaction> studentAccountTransactions = new ArrayList<>();


}
