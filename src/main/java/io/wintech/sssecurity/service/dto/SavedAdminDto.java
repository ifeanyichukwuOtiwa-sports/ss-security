package io.wintech.sssecurity.service.dto;

public record SavedAdminDto(
        String email,
        String username,
        String password
) {
}
