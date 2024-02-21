package io.wintech.sssecurity.service.dto;

import java.util.Set;

public record AssignedAccessTypeResponse(
        String roleName,
        Set<String> accessTypes
) {
}
