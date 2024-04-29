package ru.azaytsev.votingrestaurants.common.error;

import static ru.azaytsev.votingrestaurants.common.error.ErrorType.BAD_REQUEST;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg, BAD_REQUEST);
    }
}