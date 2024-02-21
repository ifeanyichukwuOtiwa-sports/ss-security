package io.wintech.sssecurity.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AppError {

    ADMIN_DUPLICATE_ERROR("Admin with email or username already exists"),
    ADMIN_INVALID_EMAIL_ERROR("Email format not accepted"),
    ADMIN_NOT_FOUND_ERROR("Admin not found"),
    ADMIN_NEW_PASSWORD_MUST_BE_DIFFERENT("New password must be different"),
    ADMIN_WRONG_PASSWORD_ERROR("Wrong password provided"),
    ACCESS_ROLE_NOT_FOUND("Access role not found"),
    ACCESS_TYPE_NOT_FOUND("Access type not found"),
    SUPER_ADMIN_CANNOT_BE_MODIFIED("Super admin cannot be modified")
    ;

    private final String message;

    public AppRequestException exception() {
        return new AppRequestException(this.name(), this.message);
    }
}
