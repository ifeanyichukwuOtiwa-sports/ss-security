package io.wintech.sssecurity.model.admin;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AccessRoleTest {

    @Test
    void hasAccess() {
        final String admin = "ADMIN";
        final String accessName = "read:api_test";
        final AccessTypeUrl typeUrl = new AccessTypeUrl("/api/test");
        final AccessType type = new AccessType(accessName, Set.of(typeUrl));
        final AccessRole accessRole = new AccessRole(admin, Instant.now(), null, Set.of(type));

        assertThat(accessRole)
                .satisfies(x -> {
                    assertThat(x.hasAccess("read:api_test")).isTrue();
                    assertThat(x.hasAccess("read:api_test_2")).isFalse();
                    assertThat(x.getAccessTypes()).contains(new AccessType(accessName));
                });
    }
}