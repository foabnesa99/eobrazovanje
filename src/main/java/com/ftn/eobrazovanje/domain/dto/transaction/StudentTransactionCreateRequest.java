package com.ftn.eobrazovanje.domain.dto.transaction;

import com.ftn.eobrazovanje.domain.common.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentTransactionCreateRequest {
    private Long studentId;
    private long amount;
    private TransactionType transactionType;
}
