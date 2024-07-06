package com.ftn.eobrazovanje.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class UserNotFoundException extends ServiceException {

    public UserNotFoundException() {
        super(ErrorCodes.USER_NOT_FOUND, "User not found");
    }

    public UserNotFoundException(String message) {
        super(ErrorCodes.USER_NOT_FOUND, message);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.NOT_FOUND;
    }
}
