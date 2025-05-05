package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.ftn.eobrazovanje.exception.ErrorCodes.SUBJECT_NOT_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubjectNotFoundException extends ServiceException{

    public SubjectNotFoundException(String message) {
        super(SUBJECT_NOT_FOUND, message);
    }

    public SubjectNotFoundException() {
        super(SUBJECT_NOT_FOUND, "Subject not found!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}
