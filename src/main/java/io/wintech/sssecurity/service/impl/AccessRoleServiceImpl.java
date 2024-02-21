package io.wintech.sssecurity.service.impl;

import io.wintech.sssecurity.exception.AppError;
import io.wintech.sssecurity.exception.AppRequestException;
import io.wintech.sssecurity.model.admin.AccessRole;
import io.wintech.sssecurity.model.admin.AccessType;
import io.wintech.sssecurity.model.admin.AccessTypeUrl;
import io.wintech.sssecurity.repository.AccessRoleRepository;
import io.wintech.sssecurity.service.api.AccessRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.wintech.sssecurity.model.admin.AccessRole.SUPERADMIN;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccessRoleServiceImpl implements AccessRoleService {
    private final AccessRoleRepository accessRoleRepository;

    @Override
    public List<AccessRole> findRoles(final String roleName) {
        if (StringUtils.isNotBlank(roleName)) {
            return this.findByName(roleName).stream().toList();
        }
        return accessRoleRepository.findAll();
    }

    @Override
    public AccessRole createRole(final String roleName) {
        final AccessRole accessRole = AccessRole.builder()
                .name(roleName)
                .createdAt(Instant.now())
                .modifiedAt(Instant.now())
                .accessTypes(Set.of())
                .build();
        return accessRoleRepository.findByNameIgnoreCase(roleName).orElse(accessRoleRepository.save(accessRole));
    }

    @Override
    public void validateRole(final String roleName) {
        accessRoleRepository.findByNameIgnoreCase(roleName)
                .orElseThrow(() -> this.roleNotFoundException(roleName));
    }

    @Override
    public Optional<AccessRole> findByName(final String roleName) {
        return accessRoleRepository.findByNameIgnoreCase(roleName);
    }

    @Override
    public AccessRole getRoleByNAme(final String roleName) {
        return findByName(roleName).orElseThrow(() -> this.roleNotFoundException(roleName));
    }

    private AppRequestException roleNotFoundException(final String roleName) {
        log.error("Role not found: {}", roleName);
        return AppError.ACCESS_ROLE_NOT_FOUND.exception();
    }

    @Override
    public void updateRole(final AccessRole accessRole) {
        findByName(accessRole.getName()).ifPresent(i -> {
            accessRoleRepository.save(i);
            syncAccessTypesInRoles(accessRole);
        });

    }
    private void syncAccessTypesInRoles(final AccessRole adminRole) {
        log.info("Syncing access types in roles");
        this.findAll()
                .stream()
                .filter(r ->!r.getName().equalsIgnoreCase(SUPERADMIN))
                .filter(r -> r.getAccessTypes().retainAll(adminRole.getAccessTypes()))
                .forEach(accessRoleRepository::save);
    }


    @Override
    public List<AccessRole> findAll() {
        return accessRoleRepository.findAll();
    }

    @Override
    public List<AccessRole> findByNameIn(final Set<String> adminRoleNames) {
        return accessRoleRepository.findByNameIgnoreCaseIn(new ArrayList<>(adminRoleNames));
    }
}
