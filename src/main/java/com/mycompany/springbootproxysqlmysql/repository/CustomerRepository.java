package com.mycompany.springbootproxysqlmysql.repository;

import com.mycompany.springbootproxysqlmysql.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
