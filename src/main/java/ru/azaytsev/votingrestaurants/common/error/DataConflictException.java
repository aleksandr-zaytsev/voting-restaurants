package ru.azaytsev.votingrestaurants.common.error;

public class DataConflictException extends AppException {
    public DataConflictException(String msg) {
        super(msg, ErrorType.DATA_CONFLICT);
    }
}