package com.ftn.eobrazovanje.domain.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentTransactionDto {
    private Long id;
    private String transactionTimestamp;
    private String transactionType;
    private long amount;
}
