package io.github.v1serviceapplication.error;

public class CannotReservePicnicTimeException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new CannotReservePicnicTimeException();

    private CannotReservePicnicTimeException() {
        super(ErrorCode.CANNOT_RESERVE_PICNIC_TIME);
    }
}
