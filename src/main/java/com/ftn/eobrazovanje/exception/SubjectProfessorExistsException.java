package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SubjectProfessorExistsException extends ServiceException{

    public SubjectProfessorExistsException(String message) {
        super(ErrorCodes.SUBJECT_PROFESSOR_ALREADY_EXISTS, message);
    }

    public SubjectProfessorExistsException() {
        super(ErrorCodes.SUBJECT_PROFESSOR_ALREADY_EXISTS, "Subject Professor already exists!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DUPLICATE_KEY;
    }

}
