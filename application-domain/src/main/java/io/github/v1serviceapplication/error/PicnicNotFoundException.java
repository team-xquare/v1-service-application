package io.github.v1serviceapplication.error;

public class PicnicNotFoundException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new PicnicNotFoundException();

    private PicnicNotFoundException() {
        super(ErrorCode.PICNIC_NOT_FOUND);
    }
}
