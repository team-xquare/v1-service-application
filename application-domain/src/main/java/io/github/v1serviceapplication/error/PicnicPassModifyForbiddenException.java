package io.github.v1serviceapplication.error;

public class PicnicPassModifyForbiddenException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new PicnicPassModifyForbiddenException();
    private PicnicPassModifyForbiddenException() {
        super(ErrorCode.PICNIC_PASS_MODIFY_FORBIDDEN);
    }
}
