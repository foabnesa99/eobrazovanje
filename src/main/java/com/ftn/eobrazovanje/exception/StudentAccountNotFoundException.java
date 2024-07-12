package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentAccountNotFoundException extends ServiceException{
    public StudentAccountNotFoundException(String message) {
        super(ErrorCodes.STUDENT_ACCOUNT_NOT_FOUND, message);
    }

    public StudentAccountNotFoundException() {
        super(ErrorCodes.STUDENT_ACCOUNT_NOT_FOUND, "Student account not found!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}
