package it.ranierivannozzi.shopping.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import it.ranierivannozzi.shopping.entity.ShoppingEntity;
import it.ranierivannozzi.shopping.entity.WarehouseEntity;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import it.ranierivannozzi.shopping.exception.ShoppingNotEditableException;
import it.ranierivannozzi.shopping.exception.ShoppingNotFoundException;
import it.ranierivannozzi.shopping.exception.WarehouseNotFoundException;
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
            .orElseThrow(() -> new WarehouseNotFoundException(warehouseId));

    ShoppingEntity shoppingEntity = ShoppingParser.toEntity(request, warehouseEntity);

    ShoppingEntity saved = shoppingRepository.save(shoppingEntity);

    return ShoppingParser.toResponse(saved, true);
  }

  @Override
  public ShoppingResponse update(Long id, ShoppingRequest request) {

    ShoppingEntity shoppingEntity =
        shoppingRepository.findById(id).orElseThrow(() -> new ShoppingNotFoundException(id));

    if (shoppingEntity.getStatus().isEditable()) {
      ShoppingEntity updated =
          shoppingRepository.save(editOnlyAvailableFields(shoppingEntity, request));
      return ShoppingParser.toResponse(updated, true);

    } else {
      throw new ShoppingNotEditableException(id);
    }
  }

  @Override
  public ShoppingResponse findById(Long id) {
    ShoppingEntity shoppingEntity =
        shoppingRepository.findById(id).orElseThrow(() -> new ShoppingNotFoundException(id));
    return ShoppingParser.toResponse(shoppingEntity, true);
  }

  @Override
  public Page<ShoppingResponse> findAll(Pageable pageable, ShoppingStatus status) {
    Page<ShoppingEntity> entityPage;

    if (status != null) {
      entityPage = shoppingRepository.findByStatus(pageable, status);
    } else {
      entityPage = shoppingRepository.findAll(pageable);
    }

    return entityPage.map(entity -> ShoppingParser.toResponse(entity, false));
  }

  @Override
  public void deleteById(Long id) {
    ShoppingEntity shoppingEntity =
        shoppingRepository.findById(id).orElseThrow(() -> new ShoppingNotFoundException(id));
    shoppingRepository.delete(shoppingEntity);
  }

  @Override
  public boolean existsById(Long id) {
    return false;
  }

  @Override
  public List<ShoppingRouteResponse> findRoutes(ShoppingStatus status) {
    return List.of();
  }

  private ShoppingEntity editOnlyAvailableFields(
      ShoppingEntity shoppingEntity, ShoppingRequest request) {
    if (request.getDeliveryLatitude() != null)
      shoppingEntity.setDeliveryLatitude(request.getDeliveryLatitude());
    if (request.getDeliveryLongitude() != null)
      shoppingEntity.setDeliveryLongitude(request.getDeliveryLongitude());
    if (request.getDeliveryAddress() != null && !request.getDeliveryAddress().isBlank())
      shoppingEntity.setDeliveryAddress(request.getDeliveryAddress());
    if (request.getCustomerName() != null && !request.getCustomerName().isBlank())
      shoppingEntity.setCustomerName(request.getCustomerName());
    if (request.getCustomerPhone() != null && !request.getCustomerPhone().isBlank())
      shoppingEntity.setCustomerPhone(request.getCustomerPhone());
    if (request.getDeliveryNotes() != null)
      shoppingEntity.setDeliveryNotes(request.getDeliveryNotes());
    if (request.getRequestedDeliveryDate() != null)
      shoppingEntity.setRequestedDeliveryDate(request.getRequestedDeliveryDate());
    return shoppingEntity;
  }
}
