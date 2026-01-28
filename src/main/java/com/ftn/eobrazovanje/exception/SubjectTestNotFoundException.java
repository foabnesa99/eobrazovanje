package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubjectTestNotFoundException extends ServiceException{

    public SubjectTestNotFoundException(String message) {
        super(ErrorCodes.SUBJECT_TEST_NOT_FOUND, message);
    }

    public SubjectTestNotFoundException() {
        super(ErrorCodes.SUBJECT_TEST_NOT_FOUND, "Subject test not found!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }

}
