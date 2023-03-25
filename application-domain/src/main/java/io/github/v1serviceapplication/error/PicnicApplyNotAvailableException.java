package io.github.v1serviceapplication.error;

public class PicnicApplyNotAvailableException extends ApplicationException {
    public static final ApplicationException EXCEPTION = new PicnicApplyNotAvailableException();

    private PicnicApplyNotAvailableException() {
        super(ErrorCode.PICNIC_APPLY_NOT_AVAILABLE);
    }
}
