package dk.capworld.cinemautils.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BookingException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -8333289589000518627L;

    public enum ErrorCode {
        OBJECT_NOT_FOUND,
        OBJECT_CANNOT_BE_SAVED,
        UNKNOWN_ERROR
        }

        private final ErrorCode errorCode;

        public BookingException() {
            this.errorCode = ErrorCode.UNKNOWN_ERROR;
        }

        public BookingException(ErrorCode errorCode, String message) {
            super(message);
            this.errorCode = errorCode;
        }

        public BookingException(ErrorCode errorCode, String message, Throwable cause) {
            super(message, cause);
            this.errorCode = errorCode;
        }

        public BookingException(ErrorCode errorCode, Throwable cause) {
            super(cause);
            this.errorCode = errorCode;
        }

}
