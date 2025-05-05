package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudyProgramNotFound extends ServiceException{

    public StudyProgramNotFound(String message) {
        super(ErrorCodes.STUDY_PROGRAM_NOT_FOUND, message);
    }

    public StudyProgramNotFound() {
        super(ErrorCodes.STUDY_PROGRAM_NOT_FOUND, "Study program not found!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}
