package com.aliniribeiro.bates.model.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String>, CustomerRepositoryCustom  {

    Optional<CustomerEntity> findCustomerById(UUID id);
}
