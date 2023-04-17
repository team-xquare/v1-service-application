package io.github.v1serviceapplication.error;

public class PicnicReserveNotAvailableException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new PicnicReserveNotAvailableException();

    private PicnicReserveNotAvailableException() {
        super(ErrorCode.PICNIC_RESERVE_NOT_AVAILABLE);
    }
}
