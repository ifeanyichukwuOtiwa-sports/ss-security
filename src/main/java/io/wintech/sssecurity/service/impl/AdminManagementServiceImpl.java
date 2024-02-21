package io.wintech.sssecurity.service.impl;

import io.wintech.sssecurity.model.admin.AccessRole;
import io.wintech.sssecurity.model.admin.AccessType;
import io.wintech.sssecurity.model.admin.Admin;
import io.wintech.sssecurity.service.api.AccessRoleService;
import io.wintech.sssecurity.service.api.AdminManagementService;
import io.wintech.sssecurity.service.api.AdminService;
import io.wintech.sssecurity.service.dto.AssignedAccessTypeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminManagementServiceImpl implements AdminManagementService {
    private final AdminService adminService;
    private final AccessRoleService accessRoleService;

    @Override
    public void unLinkAdminAndRole(final UUID adminUuid, final String roleName) {
        final Admin admin = adminService.getAdminOrFail(adminUuid);
        final AccessRole role = accessRoleService.getRoleByNAme(roleName);

        admin.getRoles().removeIf(r -> r.getName().equalsIgnoreCase(role.getName()));

        final Admin updateAdmin = adminService.updateAdmin(admin);

        log.info("Admin {} updated", updateAdmin.getAdminUuid());
    }

    @Override
    public void linkAdminAndRole(final UUID adminUuid, final String roleName) {
        final Admin admin = adminService.getAdminOrFail(adminUuid);
        final AccessRole role = accessRoleService.getRoleByNAme(roleName);
        final boolean adminAlreadyHasRoles = admin.getRoles().stream()
                .map(AccessRole::getName)
                .anyMatch(existingRoleName -> existingRoleName.equals(role.getName()));
        if (adminAlreadyHasRoles) {
            log.info("Admin {}({}) already has role {}", admin.getEmail(), admin.getAdminUuid(), role.getName());
            return;
        }
        admin.getRoles().add(role);
        final Admin updateAdmin = adminService.updateAdmin(admin);
        log.info("Admin {} updated", updateAdmin.getAdminUuid());
    }

    @Override
    public List<AssignedAccessTypeResponse> getAdminAccessTypes(final UUID adminUuid) {
        final Admin admin = adminService.getAdminOrFail(adminUuid);
        return admin.getRoles().stream()
                .map(role -> new AssignedAccessTypeResponse(
                        role.getName(),
                        role.getAccessTypes()
                                .stream()
                                .map(AccessType::getName)
                                .collect(Collectors.toSet())
                ))
                .toList();
    }

    @Override
    public boolean hasAccess(final UUID adminUuid, final String accessType) {
        final Admin admin = adminService.getAdminOrFail(adminUuid);
        final Set<String> adminRoles = admin.getRoles().stream()
                .map(AccessRole::getName)
                .collect(Collectors.toSet());

        return accessRoleService.findByNameIn(adminRoles)
                .stream()
                .anyMatch(role -> role.hasAccess(accessType));
    }
}
