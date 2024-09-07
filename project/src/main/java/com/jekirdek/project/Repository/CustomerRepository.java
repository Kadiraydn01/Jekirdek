package com.jekirdek.project.Repository;

import com.jekirdek.project.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByFirstName(String firstName);
    Optional<Customer> findByLastName(String lastName);
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByRegion(String region);
}
