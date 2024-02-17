package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.user.Customer;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


class CustomerRepositoryTest extends BasePersistenceTest {

    @Autowired
    private CustomerRepository sut;

    @Test
    void findByUsername() {
        final Customer customer = Customer.builder()
                .username("test")
                .password("test")
                .email("test@test.com")
                .phoneNumber("+2347036771035")
                .country("Nigeria")
                .firstName("John")
                .lastName("Doe")
                .build();
        final LocalDateTime now = LocalDateTime.now();
        sut.saveAndFlush(customer);


        final Optional<Customer> test = sut.findByUsername("test");

        System.out.println(test);

        assertThat(test)
                .isPresent()
                .hasValueSatisfying(x -> {
                    assertThat(x.getUsername()).isEqualTo("test");
                    assertThat(x.getPassword()).isEqualTo("test");
                    assertThat(x.getEmail()).isEqualTo("test@test.com");
                    assertThat(x.getPhoneNumber()).isEqualTo("+2347036771035");
                    assertThat(x.getCreatedAt()).isEqualToIgnoringSeconds(now);
                });
    }

    @Test
    void findByEmail() {

        final Customer customer = Customer.builder()
                .username("test")
                .password("test")
                .email("test@test.com")
                .phoneNumber("+2347036771035")
                .country("Nigeria")
                .firstName("John")
                .lastName("Doe")
                .build();
        final LocalDateTime now = LocalDateTime.now();
        sut.saveAndFlush(customer);

        final Optional<Customer> test = sut.findByEmail("test@test.com");

        AssertionsForClassTypes.assertThat(test)
                .isPresent()
                .hasValueSatisfying(x -> {
                    AssertionsForClassTypes.assertThat(x.getUsername()).isEqualTo("test");
                    AssertionsForClassTypes.assertThat(x.getPassword()).isEqualTo("test");
                    AssertionsForClassTypes.assertThat(x.getEmail()).isEqualTo("test@test.com");
                    AssertionsForClassTypes.assertThat(x.getPhoneNumber()).isEqualTo("+2347036771035");
                    AssertionsForClassTypes.assertThat(x.getCreatedAt()).isEqualToIgnoringSeconds(now);
                });
    }

    @Test
    void findByPhoneNumberAndCountry() {
        final Customer customer = Customer.builder()
                .username("test")
                .password("test")
                .email("test@test.com")
                .phoneNumber("+2347036771035")
                .country("Nigeria")
                .firstName("John")
                .lastName("Doe")
                .build();
        final LocalDateTime now = LocalDateTime.now();
        sut.saveAndFlush(customer);

        final Optional<Customer> test = sut.findByPhoneNumberAndCountry("+2347036771035", "Nigeria");

        AssertionsForClassTypes.assertThat(test)
                .isPresent()
                .hasValueSatisfying(x -> {
                    AssertionsForClassTypes.assertThat(x.getUsername()).isEqualTo("test");
                    AssertionsForClassTypes.assertThat(x.getPassword()).isEqualTo("test");
                    AssertionsForClassTypes.assertThat(x.getEmail()).isEqualTo("test@test.com");
                    AssertionsForClassTypes.assertThat(x.getPhoneNumber()).isEqualTo("+2347036771035");
                    AssertionsForClassTypes.assertThat(x.getCreatedAt()).isEqualToIgnoringSeconds(now);
                });
    }

    @Test
    @Sql(
            statements = {
                    """
                    INSERT INTO customer
                    VALUES (
                    UUID_TO_BIN(UUID())
                   , 'test'
                   , 'test'
                   , 'test@test.com'
                   , 'John'
                   , 'Doe'
                   , '+2347036771035'
                   , 'Nigeria'
                   , FALSE
                   , NOW()
                   , NULL
                   , NULL
                   , FALSE
                    );
                   """
            }
    )
    void findByUsername_() {
        final LocalDateTime now = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime();
        final Optional<Customer> test = sut.findByUsername("test");

        AssertionsForClassTypes.assertThat(test)
                .isPresent()
                .hasValueSatisfying(x -> {
                    AssertionsForClassTypes.assertThat(x.getUsername()).isEqualTo("test");
                    AssertionsForClassTypes.assertThat(x.getPassword()).isEqualTo("test");
                    AssertionsForClassTypes.assertThat(x.getEmail()).isEqualTo("test@test.com");
                    AssertionsForClassTypes.assertThat(x.getPhoneNumber()).isEqualTo("+2347036771035");
                    AssertionsForClassTypes.assertThat(x.getCreatedAt()).isEqualToIgnoringSeconds(now);
                });
    }
}