package dk.capworld.cinemautils.exceptions;

public class BookingException extends RuntimeException {

        public enum ErrorCode {
                ObjectNotFound,
                NotNull,
                InvalidArgument,
                InvalidState,
                TypeMismatch,
                MissingId,
                DataIntegrityViolationException,
                InvalidFileFormat,
                InvalidError,
                ObjectCannotBeSaved,
                InsufficientPermissions,
                SystemError,
                UniquenessViolationError,
                InvalidDateRange,
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

        public ErrorCode getErrorCode() {
            return this.errorCode;
        }

    }
