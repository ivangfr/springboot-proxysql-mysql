package com.mycompany.customerapi.repository;

import com.mycompany.customerapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
