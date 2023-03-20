package io.github.v1serviceapplication.error;

public class PicnicReservationNotFoundException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new PicnicReservationNotFoundException();

    private PicnicReservationNotFoundException() {
        super(ErrorCode.PICNIC_RESERVATION_NOT_FOUND);
    }
}
