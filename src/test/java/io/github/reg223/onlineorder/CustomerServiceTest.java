package io.github.reg223.onlineorder;

import io.github.reg223.onlineorder.entities.CartEntity;
import io.github.reg223.onlineorder.entities.CustomerEntity;
import io.github.reg223.onlineorder.repos.CartRepository;
import io.github.reg223.onlineorder.repos.CustomerRepository;
import io.github.reg223.onlineorder.services.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserDetailsManager userDetailsManager;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository, cartRepository, passwordEncoder, userDetailsManager);
    }

    @Test
    void getCustomerByEmail_shouldReturnCustomer(){
        String email = "customer@example.com";
        CustomerEntity customerEntity = new CustomerEntity(1L,email,"password",true,"First","Last");

        Mockito.when(customerRepository.findByEmail(email)).thenReturn(customerEntity);

        CustomerEntity customer = customerService.getCustomerByEmail(email);

        Assertions.assertEquals(customerEntity, customer);

    }

    @Test
    void signUp_shouldCreateUserUpdateNameAndCreateCart(){
        String email = "CUSTOMER@EXAMPLE.COM";
        String lowerCaseEmail = "customer@example.com";
        String password = "password";
        String encodedPassword = "encoded-password";
        String firstName = "First";
        String lastName = "Last";

        CustomerEntity customerEntity = new CustomerEntity(3L,lowerCaseEmail,encodedPassword,true,firstName,lastName);

        Mockito.when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        Mockito.when(customerRepository.findByEmail(lowerCaseEmail)).thenReturn(customerEntity);

        customerService.SignUp(email,password,firstName,lastName);

        ArgumentCaptor<UserDetails> userCaptor = ArgumentCaptor.forClass(UserDetails.class);
        Mockito.verify(userDetailsManager).createUser(userCaptor.capture());
        Assertions.assertEquals(lowerCaseEmail, userCaptor.getValue().getUsername());
        Assertions.assertEquals(encodedPassword, userCaptor.getValue().getPassword());
        Assertions.assertTrue(userCaptor.getValue().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER")));
        Mockito.verify(customerRepository).updateNameByEmail(lowerCaseEmail,firstName,lastName);
        Mockito.verify(cartRepository).save(new CartEntity(null,customerEntity.id(),0.0));

    }
}
