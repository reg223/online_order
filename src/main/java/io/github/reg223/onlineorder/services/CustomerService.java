package io.github.reg223.onlineorder.services;

import io.github.reg223.onlineorder.entities.CartEntity;
import io.github.reg223.onlineorder.entities.CustomerEntity;
import io.github.reg223.onlineorder.repos.CartRepository;
import io.github.reg223.onlineorder.repos.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;
    public CustomerService(CustomerRepository customerRepository,
                           CartRepository cartRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsManager userDetailsManager) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
    }

    public CustomerEntity getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Transactional
    public void SignUp(String email, String password, String firstName, String lastName) {
        email = email.toLowerCase();
        UserDetails user = User.builder()
                .username(email)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();
        userDetailsManager.createUser(user);
        customerRepository.updateNameByEmail(email, firstName, lastName);

        CustomerEntity nCustomer = customerRepository.findByEmail(email);
        CartEntity cart = new CartEntity(null,nCustomer.id(),0.0);
        cartRepository.save(cart);

    }
}
