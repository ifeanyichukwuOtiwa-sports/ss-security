package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.admin.AccessType;
import io.wintech.sssecurity.model.admin.AccessTypeUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AccessTypeRepositoryTest extends BasePersistenceTest {

    @Autowired
    private AccessTypeRepository accessTypeRepository;

    @Autowired
    private AccessTypeUrlRepository accessTypeUrlRepository;

    @Test
    void findByName() {
        final String name = "read:repository";
        final AccessTypeUrl typeUrl = new AccessTypeUrl("/api/read/repository");

        accessTypeUrlRepository.saveAndFlush(typeUrl);

        final AccessType entity = new AccessType(name, Set.of(typeUrl));

        final AccessType expected = accessTypeRepository.saveAndFlush(entity);

        final Optional<AccessType> result = accessTypeRepository.findByName(name);
        assertThat(result)
                .isPresent()
                .hasValueSatisfying(value -> assertThat(value).usingRecursiveComparison().isEqualTo(expected))
                .hasValue(expected);
    }



}