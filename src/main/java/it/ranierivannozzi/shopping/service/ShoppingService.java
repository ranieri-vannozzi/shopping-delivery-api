package it.ranierivannozzi.shopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import it.ranierivannozzi.shopping.model.request.ShoppingRequest;
import it.ranierivannozzi.shopping.model.response.ShoppingResponse;
import it.ranierivannozzi.shopping.model.response.ShoppingRouteResponse;

@Service
public class ShoppingService implements IShoppingService {

  @Override
  public ShoppingResponse save(ShoppingRequest request) {
    return null;
  }

  @Override
  public Optional<ShoppingResponse> findById(Long id) {
    return Optional.empty();
  }

  @Override
  public List<ShoppingResponse> findAll() {
    return List.of();
  }

  @Override
  public List<ShoppingResponse> findByStatus(ShoppingStatus status) {
    return List.of();
  }

  @Override
  public void deleteById(Long id) {}

  @Override
  public boolean existsById(Long id) {
    return false;
  }

  @Override
  public List<ShoppingRouteResponse> findRoutes(ShoppingStatus status) {
    return List.of();
  }
}
