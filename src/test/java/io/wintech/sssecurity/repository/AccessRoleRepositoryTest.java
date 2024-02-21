package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.admin.AccessRole;
import io.wintech.sssecurity.model.admin.AccessType;
import io.wintech.sssecurity.model.admin.AccessTypeUrl;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AccessRoleRepositoryTest extends BasePersistenceTest {

    @Test
    void findByName() {
        final String accessName = "read:api_test";

        final AccessTypeUrl typeUrl = accessTypeUrlRepository.saveAndFlush(new AccessTypeUrl("/api/test"));
        final AccessType type = new AccessType(accessName, Set.of(typeUrl));

        final AccessType accessType = accessTypeRepository.saveAndFlush(type);

        final String admin = "ADMIN";
        final AccessRole entity = new AccessRole(admin, Instant.now(), null, Set.of(accessType));

        final AccessRole expectedAccessRole = accessRoleRepository.save(entity);

        final Optional<AccessRole> result = accessRoleRepository.findByNameIgnoreCase(admin);

        assertThat(result).isPresent()
                .hasValueSatisfying(value -> assertThat(value).usingRecursiveComparison()
                        .isEqualTo(expectedAccessRole));
    }
}