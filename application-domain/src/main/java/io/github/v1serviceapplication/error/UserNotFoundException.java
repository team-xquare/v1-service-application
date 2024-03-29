package io.github.v1serviceapplication.error;

public class UserNotFoundException extends ApplicationException{

    public static final ApplicationException EXCEPTION = new UserNotFoundException();
    private UserNotFoundException() {
        super(ErrorCode.USER_ID_NOT_FOUND);
    }
}
