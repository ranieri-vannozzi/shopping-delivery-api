package it.ranierivannozzi.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.ranierivannozzi.shopping.entity.ShoppingEntity;

@Repository
public interface ShoppingRepository extends JpaRepository<ShoppingEntity, Long> {}
