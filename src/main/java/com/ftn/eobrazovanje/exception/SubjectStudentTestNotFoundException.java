package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubjectStudentTestNotFoundException extends ServiceException{


    public SubjectStudentTestNotFoundException(String message) {
        super(ErrorCodes.SUBJECT_STUDENT_TEST_NOT_FOUND, message);
    }

    public SubjectStudentTestNotFoundException() {
        super(ErrorCodes.SUBJECT_STUDENT_TEST_NOT_FOUND, "Subject Student Test Not Found!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}
