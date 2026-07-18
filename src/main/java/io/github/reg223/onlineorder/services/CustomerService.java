package io.github.reg223.onlineorder.services;

import io.github.reg223.onlineorder.entities.CustomerEntity;
import io.github.reg223.onlineorder.repos.CustomerRepository;

public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
