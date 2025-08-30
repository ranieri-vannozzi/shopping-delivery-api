package it.ranierivannozzi.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.ranierivannozzi.shopping.entity.ShoppingEntity;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;

@Repository
public interface ShoppingRepository extends JpaRepository<ShoppingEntity, Long> {

  Page<ShoppingEntity> findByStatus(Pageable pageable, ShoppingStatus status);
}
