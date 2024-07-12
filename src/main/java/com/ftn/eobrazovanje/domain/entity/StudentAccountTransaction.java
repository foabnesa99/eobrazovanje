package com.ftn.eobrazovanje.domain.entity;

import com.ftn.eobrazovanje.domain.common.TransactionType;
import com.ftn.eobrazovanje.domain.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import javax.persistence.JoinColumn;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table
public class StudentAccountTransaction extends BaseEntity {


    private LocalDateTime transactionTimestamp;

    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudentAccount studentAccount;

    private long amount;

}
