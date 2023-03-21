package io.github.v1serviceapplication.error;

public class CannotReservePicnicException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new CannotReservePicnicException();

    private CannotReservePicnicException() {
        super(ErrorCode.CANNOT_RESERVE_PICNIC_TIME);
    }
}
