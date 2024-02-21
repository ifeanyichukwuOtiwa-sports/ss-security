package io.wintech.sssecurity.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AppException extends RuntimeException{
    @Getter
    private String uuid = UUID.randomUUID().toString();
    private final String message;
    @Getter
    private final String key;
    private List<String> params = List.of();
    private Map<String, Object> payload = Map.of();

    public AppException(String key, String message) {
        super(message);
        this.message = message;
        this.key = key;
    }

    public AppException(String key, String message, Exception e) {
        super(message, e);
        this.message = message;
        this.key = key;
    }

    public AppException setParams(List<String> params) {
        if (params != null) {
            this.params = params;
        }

        return this;
    }

    public AppException setParams(String... params) {
        if (params != null) {
            this.params = Arrays.asList(params);
        }

        return this;
    }

    public AppException setPayload(Map<String, Object> payload) {
        if (payload != null) {
            this.payload = payload;
        }

        return this;
    }

    @Override
    public String getMessage() {
        return this.uuid + " " + this.message;
    }
    public final void setRealUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getShortMessage() {
        return this.message;
    }

}
