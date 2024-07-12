package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SubjectStudentAttendanceNotFoundException extends ServiceException{


    public SubjectStudentAttendanceNotFoundException(String message) {
        super(ErrorCodes.SUBJECT_STUDENT_ATTENDANCE_NOT_FOUND, message);
    }

    public SubjectStudentAttendanceNotFoundException() {
        super(ErrorCodes.SUBJECT_STUDENT_ATTENDANCE_NOT_FOUND, "Subject student attendance not found");
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}
