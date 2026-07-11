package io.github.reg223.onlineorder.repos;

import io.github.reg223.onlineorder.entities.CustomerEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CustomerRepository extends ListCrudRepository<CustomerEntity,Long> {
    List<CustomerEntity> findByLastName(String lastName);
    List<CustomerEntity> findByFirstName(String firstName);

    CustomerEntity findByEmail(String email);

    @Modifying
    @Query("UPDATE customers SET first_name= :firstName, last_name = :lastName, email = :email")
    void updateNameByEmail(String email, String firstName, String lastName);
}
