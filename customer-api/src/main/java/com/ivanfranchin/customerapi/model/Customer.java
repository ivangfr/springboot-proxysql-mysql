package com.ivanfranchin.customerapi.model;

import com.ivanfranchin.customerapi.rest.dto.CreateCustomerRequest;
import com.ivanfranchin.customerapi.rest.dto.UpdateCustomerRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void onPrePersist() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = Instant.now();
    }

    public static Customer from(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(createCustomerRequest.firstName());
        customer.setLastName(createCustomerRequest.lastName());
        return customer;
    }

    public static void updateFrom(UpdateCustomerRequest updateCustomerRequest, Customer customer) {
        if (updateCustomerRequest.firstName() != null) {
            customer.setFirstName(updateCustomerRequest.firstName());
        }
        if (updateCustomerRequest.lastName() != null) {
            customer.setLastName(updateCustomerRequest.lastName());
        }
    }
}
