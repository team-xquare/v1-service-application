package io.github.v1serviceapplication.error;

public class UserNotEmptyException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new UserNotEmptyException();
    private UserNotEmptyException() {
        super(ErrorCode.USER_NOT_EMPTY);
    }
}
