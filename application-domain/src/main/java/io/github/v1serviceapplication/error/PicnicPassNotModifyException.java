package io.github.v1serviceapplication.error;

public class PicnicPassNotModifyException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new PicnicPassNotModifyException();
    private PicnicPassNotModifyException() {
        super(ErrorCode.PICNIC_PASS_NOT_MODIFY);
    }
}
