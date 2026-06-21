package com.ftn.eobrazovanje.domain.entity.user;


import com.ftn.eobrazovanje.domain.entity.StudentAccount;
import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.stereotype.Indexed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Indexed
@Entity(name = "student")
@Table
@SQLDelete(sql = "UPDATE student SET deleted = true WHERE id = ? and `version` = ?")
@SQLRestriction("deleted = false")
public class Student extends BaseEntity{

    @OneToOne(fetch = FetchType.EAGER, cascade = jakarta.persistence.CascadeType.ALL)
    private User user;

    private String studentId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StudentAccount studentAccount;

}
