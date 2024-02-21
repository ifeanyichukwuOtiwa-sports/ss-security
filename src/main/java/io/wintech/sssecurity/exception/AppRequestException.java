package io.wintech.sssecurity.exception;

import java.util.List;

public class AppRequestException extends AppException {
    public AppRequestException(final String message) {
        super("BAD_REQUEST", message);
    }
    public AppRequestException(final String key, final String message) {
        super(key, message);
    }
    public AppRequestException(final String key, final String message, final Exception e) {
        super(key, message, e);
    }
    public AppRequestException(String key, String message, List<String> params) {
        super(key, message);
        this.setParams(params);
    }
}
