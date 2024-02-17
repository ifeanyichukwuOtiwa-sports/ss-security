package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.admin.AccessTypeUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AccessTypeUrlRepositoryTest extends BasePersistenceTest {

    @Test
    void findByUrl() {
        assertThat(accessTypeUrlRepository.findByUrl("/api/test"))
                .isEmpty();
    }

    @Test
    void findByUrl_with_url() {
        accessTypeUrlRepository.saveAndFlush(new AccessTypeUrl("/api/test"));

        assertThat(accessTypeUrlRepository.findByUrl("/api/test"))
               .isNotEmpty()
                .hasValueSatisfying(t -> assertThat(t.getUrl()).isEqualTo("/api/test"));
    }

}