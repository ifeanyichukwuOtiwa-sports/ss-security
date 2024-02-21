package io.wintech.sssecurity.service.dto;

import jakarta.validation.constraints.NotEmpty;

public record EditAdminDetailRequest(
        String newPassword,
        String password,
        @NotEmpty String firstname,
        @NotEmpty String lastname
) {
}
