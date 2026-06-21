package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.ftn.eobrazovanje.exception.ErrorCodes.STUDENT_DOCUMENT_NOT_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentDocumentNotFoundException extends ServiceException {

    public StudentDocumentNotFoundException(String message) {
        super(STUDENT_DOCUMENT_NOT_FOUND, message);
    }

    public StudentDocumentNotFoundException() {
        super(STUDENT_DOCUMENT_NOT_FOUND, "Student document not found!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}
