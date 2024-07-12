package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentAccountTransactionNotFoundException extends ServiceException{

    public StudentAccountTransactionNotFoundException(String message) {
        super(ErrorCodes.STUDENT_ACCOUNT_TRANSACTION_NOT_FOUND, message);
    }

    public StudentAccountTransactionNotFoundException() {
        super(ErrorCodes.STUDENT_ACCOUNT_TRANSACTION_NOT_FOUND, "Student account transaction not found!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}
