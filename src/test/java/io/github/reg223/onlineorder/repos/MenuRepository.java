package io.github.reg223.onlineorder.repos;

import io.github.reg223.onlineorder.entities.CustomerEntity;
import io.github.reg223.onlineorder.entities.MenuEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MenuRepository extends ListCrudRepository<MenuEntity,Long> {
    List<MenuEntity> getByShopId(Long shopId);
}
