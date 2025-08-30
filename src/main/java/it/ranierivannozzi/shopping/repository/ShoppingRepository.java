package it.ranierivannozzi.shopping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.ranierivannozzi.shopping.entity.ShoppingEntity;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;

@Repository
public interface ShoppingRepository extends JpaRepository<ShoppingEntity, Long> {

  List<ShoppingEntity> findByStatus(ShoppingStatus status);
}
