package io.github.v1serviceapplication.error;

public class UserNotEmptyException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new UserNotEmptyException();
    public UserNotEmptyException() {
        super(ErrorCode.USER_ID_EXIST);
    }
}
