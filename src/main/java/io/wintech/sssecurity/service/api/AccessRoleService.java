package io.wintech.sssecurity.service.api;

import io.wintech.sssecurity.model.admin.AccessRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccessRoleService {
    List<AccessRole> findRoles(String roleName);

    AccessRole createRole(String role);

    void validateRole(String roleName);

    Optional<AccessRole> findByName(String roleName);
    AccessRole getRoleByNAme(String roleName);

    void updateRole(AccessRole accessRole);

    List<AccessRole> findAll();

    List<AccessRole> findByNameIn(Set<String> adminRoleNames);
}
