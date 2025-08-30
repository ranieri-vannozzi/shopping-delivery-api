package it.ranierivannozzi.shopping.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import it.ranierivannozzi.shopping.entity.ShoppingEntity;
import it.ranierivannozzi.shopping.entity.WarehouseEntity;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import it.ranierivannozzi.shopping.model.request.ShoppingRequest;
import it.ranierivannozzi.shopping.model.response.ShoppingResponse;
import it.ranierivannozzi.shopping.model.response.ShoppingRouteResponse;
import it.ranierivannozzi.shopping.repository.ShoppingRepository;
import it.ranierivannozzi.shopping.repository.WarehouseRepository;
import it.ranierivannozzi.shopping.utils.ShoppingParser;

@Service
public class ShoppingService implements IShoppingService {

  private final ShoppingRepository shoppingRepository;
  private final WarehouseRepository warehouseRepository;

  public ShoppingService(
      ShoppingRepository shoppingRepository, WarehouseRepository warehouseRepository) {
    this.warehouseRepository = warehouseRepository;
    this.shoppingRepository = shoppingRepository;
  }

  @Override
  public ShoppingResponse save(ShoppingRequest request) {

    Long warehouseId = request.getWarehouseId();

    WarehouseEntity warehouseEntity =
        warehouseRepository
            .findById(warehouseId)
            .orElseThrow(
                () -> new IllegalArgumentException("Warehouse not found. Id: " + warehouseId));

    ShoppingEntity shoppingEntity = ShoppingParser.toEntity(request, warehouseEntity);

    ShoppingEntity saved = shoppingRepository.save(shoppingEntity);

    return ShoppingParser.toResponse(saved);
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
