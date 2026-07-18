package io.github.reg223.onlineorder.repos;

import io.github.reg223.onlineorder.entities.OrderEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByCartId(Long cartId);
    OrderEntity findByCartIdAndMenuItemId(Long cartId ,Long orderId);

    @Modifying
    @Query("DELETE FROM order_items WHERE cart_id = :cartId")
    void deleteByCartId(Long cartId);

    @Modifying
    @Query("DELETE FROM order_items WHERE cart_id = :cartId AND menu_item_id = :itemId")
    void deleteByCartIdAndMenuItemId(Long cartId, Long itemId);
}
