package io.wintech.sssecurity.service.impl;

import com.google.common.collect.Sets;
import io.wintech.sssecurity.exception.AppError;
import io.wintech.sssecurity.model.admin.AccessRole;
import io.wintech.sssecurity.model.admin.AccessType;
import io.wintech.sssecurity.model.admin.AccessTypeUrl;
import io.wintech.sssecurity.repository.AccessTypeRepository;
import io.wintech.sssecurity.repository.AccessTypeUrlRepository;
import io.wintech.sssecurity.service.api.AccessManagementService;
import io.wintech.sssecurity.service.api.AccessRoleService;
import io.wintech.sssecurity.service.api.AccessTypeService;
import io.wintech.sssecurity.service.dto.AccessRoleTypeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.wintech.sssecurity.model.admin.AccessRole.SUPERADMIN;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccessManagementServiceImpl implements AccessManagementService {
    private final AccessTypeUrlRepository accessTypeUrlRepository;
    private final AccessRoleService accessRoleService;
    private final AccessTypeService accessTypeService;

    @Override
    public AccessRole updateAccessTypesInAdmin(final Map<String, List<String>> accessTypeUrls) {
        final AccessRole adminRole = accessRoleService.findByName(SUPERADMIN)
                .orElseGet(() -> accessRoleService.createRole(SUPERADMIN));
        final List<AccessType> accessTypes = accessTypeUrls.entrySet()
                .stream()
                .map(t -> new AccessType(t.getKey(), mapToAccessTypeUrs(t.getValue())))
                .toList();

        final Set<AccessType> accessTypeHashSet = accessTypeService.saveAll(accessTypes);

        adminRole.setAccessTypes(accessTypeHashSet);
        accessRoleService.updateRole(adminRole);
        return null;
    }
    @Override
    public AccessRole createNewRole(final String roleName) {
        return accessRoleService.createRole(roleName);
    }

    @Override
    public AccessRole addAccessType(final String role, final String type) {
        final AccessRole accessRole = getAccessRole(role);

        final AccessRole superAdminRole = getAccessRole(SUPERADMIN);
        final AccessType accessType = superAdminRole.getAccessTypes()
                .stream()
                .filter(a -> a.getName().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Access type not found: {}", type);
                    return AppError.ACCESS_TYPE_NOT_FOUND.exception();
                });
        accessRole.getAccessTypes().add(accessType);

        accessRoleService.updateRole(accessRole);
        return accessRole;
    }

    @Override
    public AccessRole getAccessRole(final String role) {
        return accessRoleService.findByName(role).orElseThrow(() -> {
            log.error("Access role not found: {}", role);
            return AppError.ACCESS_ROLE_NOT_FOUND.exception();
        });
    }

    @Override
    public List<AccessRole> getAccessRoles() {
        return accessRoleService.findAll();
    }

    @Override
    public AccessRole removeAccessType(final String role, final String type) {
        if (SUPERADMIN.equalsIgnoreCase(role)) {
            throw AppError.SUPER_ADMIN_CANNOT_BE_MODIFIED.exception();
        }

        final AccessRole accessRole = getAccessRole(role);

        accessRole.getAccessTypes().removeIf(t -> t.getName().equalsIgnoreCase(type));
        accessRoleService.updateRole(accessRole);
        return accessRole;
    }

    @Override
    public AccessRoleTypeResponse getRoleAccessTypes(final String role) {
        final AccessRole superAdminRole = getAccessRole(SUPERADMIN);
        final AccessRole accessRole = getAccessRole(role);
        return generateRoleAccessType(accessRole, superAdminRole);
    }

    private Set<AccessTypeUrl> mapToAccessTypeUrs(final List<String> urls) {
        final List<AccessTypeUrl> accessTypeUrls = urls.stream()
                .map(AccessTypeUrl::new)
                .toList();
        final List<AccessTypeUrl> typeUrlList = accessTypeUrlRepository.saveAll(accessTypeUrls);
        return new HashSet<>(typeUrlList);
    }

    private AccessRoleTypeResponse generateRoleAccessType(final AccessRole accessRole, final AccessRole superAdminRole) {
        final Set<AccessType> enabledAccessTypes = accessRole.getAccessTypes();
        final Set<AccessType> superAdminTypes = superAdminRole.getAccessTypes();
        final Set<AccessType> disabledAccessTypes = Sets.difference(new HashSet<>(superAdminTypes), enabledAccessTypes);
        return AccessRoleTypeResponse.from(enabledAccessTypes, disabledAccessTypes);
    }
}
