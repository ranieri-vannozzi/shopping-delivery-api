package it.ranierivannozzi.shopping.repository;

import it.ranierivannozzi.shopping.entity.ShoppingEntity;
import it.ranierivannozzi.shopping.entity.WarehouseEntity;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingRepository extends JpaRepository<ShoppingEntity, Long> {

  List<ShoppingEntity> findByStatus(ShoppingStatus status);

  Page<ShoppingEntity> findByStatus(Pageable pageable, ShoppingStatus status);

  List<ShoppingEntity> findByWarehouseAndStatus(WarehouseEntity warehouse, ShoppingStatus status);
}
