package io.github.v1serviceapplication.error;

public class InvalidPicnicApplicationTimeException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new InvalidPicnicApplicationTimeException();

    private InvalidPicnicApplicationTimeException() {
        super(ErrorCode.INVALID_PICNIC_TIME);
    }
}