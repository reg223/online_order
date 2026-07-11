package io.github.reg223.onlineorder.repos;

import io.github.reg223.onlineorder.entities.ShopEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ShopRepository extends ListCrudRepository<ShopEntity,Long> {
}
