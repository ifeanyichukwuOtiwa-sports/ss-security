package io.wintech.sssecurity.service.api;

import io.wintech.sssecurity.service.dto.AssignedAccessTypeResponse;

import java.util.List;
import java.util.UUID;

public interface AdminManagementService {
    void unLinkAdminAndRole(UUID adminUuid, String roleId);
    void linkAdminAndRole(UUID adminUuid, String roleId);
    List<AssignedAccessTypeResponse> getAdminAccessTypes(UUID adminUuid);
    boolean hasAccess(UUID adminUuid, String accessType);
}
