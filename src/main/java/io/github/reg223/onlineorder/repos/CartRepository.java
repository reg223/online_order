package io.github.reg223.onlineorder.repos;

import io.github.reg223.onlineorder.entities.CartEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CartRepository extends ListCrudRepository<CartEntity, Long> {
    List<CartEntity> findByCustomerId(Long customerId);

//

    @Modifying
    @Query("UPDATE carts SET price= :price WHERE id = :cartId")
    void updateByCartId(Long cartId, Double price);
}
