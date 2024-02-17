package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByUsername(String username);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNumberAndCountry(String phoneNumber, String country);
}
