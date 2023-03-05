package io.github.v1serviceapplication.error;

public class UserExistException extends ApplicationException {

    public static final ApplicationException EXCEPTION = new UserExistException();
    public UserExistException() {
        super(ErrorCode.USER_ID_EXIST);
    }
}
