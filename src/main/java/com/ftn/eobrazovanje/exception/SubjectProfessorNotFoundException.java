package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubjectProfessorNotFoundException extends ServiceException{
    public SubjectProfessorNotFoundException(String message) {
        super(ErrorCodes.SUBJECT_PROFESSOR_NOT_FOUND, message);
    }

    public SubjectProfessorNotFoundException() {
        super(ErrorCodes.SUBJECT_PROFESSOR_NOT_FOUND, "Subject Professor Not Found!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}
