package io.github.v1serviceapplication.error;

public class JsonConvertException extends ApplicationException {

    public static final ApplicationException EXCEPTION =
            new JsonConvertException();

    private JsonConvertException() {
        super(ErrorCode.JSON_CONVERT_ERROR);
    }
}
