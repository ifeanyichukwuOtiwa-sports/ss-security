package io.wintech.sssecurity.service.api;

import io.wintech.sssecurity.model.admin.AccessRole;
import io.wintech.sssecurity.service.dto.AccessRoleTypeResponse;

import java.util.List;
import java.util.Map;

public interface AccessManagementService {
    AccessRole updateAccessTypesInAdmin(Map<String, List<String>> accessTypeUrls);
    AccessRole createNewRole(String roleName);
    AccessRole addAccessType(String role, String type);
    AccessRole getAccessRole(String role);
    List<AccessRole> getAccessRoles();
    AccessRole removeAccessType(String role, String type);
    AccessRoleTypeResponse getRoleAccessTypes(String role);
}
