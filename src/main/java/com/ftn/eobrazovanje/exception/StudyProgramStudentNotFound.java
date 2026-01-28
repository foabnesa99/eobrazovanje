package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.ftn.eobrazovanje.exception.ErrorCodes.STUDY_PROGRAM_STUDENT_NOT_FOUND;
import static com.ftn.eobrazovanje.exception.ErrorCodes.SUBJECT_NOT_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudyProgramStudentNotFound extends ServiceException{


    public StudyProgramStudentNotFound(String message) {
        super(STUDY_PROGRAM_STUDENT_NOT_FOUND, message);
    }

    public StudyProgramStudentNotFound() {
        super(STUDY_PROGRAM_STUDENT_NOT_FOUND, "Study Program Student not found!");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }

}
