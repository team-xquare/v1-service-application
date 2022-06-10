package io.github.v1serviceapplication.error;

public class StayNotFoundException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new StayNotFoundException();

    private StayNotFoundException() {
        super(ErrorCode.STAY_NOT_FOUND);
    }
}
