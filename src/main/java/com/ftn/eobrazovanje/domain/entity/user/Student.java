package com.ftn.eobrazovanje.domain.entity.user;


import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.stereotype.Indexed;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Indexed
@Entity(name = "student")
@Table
public class Student extends BaseEntity{

    @OneToOne(fetch = FetchType.EAGER, cascade = jakarta.persistence.CascadeType.ALL)
    private User user;

    private String studentId;

}
