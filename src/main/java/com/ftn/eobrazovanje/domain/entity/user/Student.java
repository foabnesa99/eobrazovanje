package com.ftn.eobrazovanje.domain.entity.user;


import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.springframework.stereotype.Indexed;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Indexed
@Entity(name = "student")
@Table(name = "`student`", uniqueConstraints = @UniqueConstraint(name = "email_idx", columnNames = {"email"}))
@SQLDelete(sql = "UPDATE student SET deleted = true WHERE id = ?")
public class Student extends User{

    private String studentId;

}
