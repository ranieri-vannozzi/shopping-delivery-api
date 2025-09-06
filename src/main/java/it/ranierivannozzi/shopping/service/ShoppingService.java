package it.ranierivannozzi.shopping.service;

import com.google.maps.model.DirectionsResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import it.ranierivannozzi.shopping.utils.RouteUtils;
import it.ranierivannozzi.shopping.utils.ShoppingParser;

@Service
public class ShoppingService implements IShoppingService {

  private final ShoppingRepository shoppingRepository;
  private final WarehouseRepository warehouseRepository;
  private final RouteService routeService;

  public ShoppingService(
      ShoppingRepository shoppingRepository,
      WarehouseRepository warehouseRepository,
      RouteService routeService) {
    this.warehouseRepository = warehouseRepository;
    this.shoppingRepository = shoppingRepository;
    this.routeService = routeService;
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
  public ShoppingRouteResponse findRoutes(Long warehouseId, ShoppingStatus status) {

    WarehouseEntity warehouse =
        warehouseRepository
            .findById(warehouseId)
            .orElseThrow(() -> new WarehouseNotFoundException(warehouseId));

    List<ShoppingEntity> list = shoppingRepository.findByWarehouseAndStatus(warehouse, status);

    Double warehouseLatitude = warehouse.getLatitude();
    Double warehouseLongitude = warehouse.getLongitude();

    Map<Double, Double> waypoints = new HashMap<Double, Double>();

    for (ShoppingEntity shoppingEntity : list) {
      Double deliveryLatitude = shoppingEntity.getDeliveryLatitude();
      Double deliveryLongitude = shoppingEntity.getDeliveryLongitude();
      waypoints.put(deliveryLatitude, deliveryLongitude);
    }

    DirectionsResult optimizedDirections =
        routeService.getOptimizedRoute(
            warehouseLatitude,
            warehouseLongitude,
            warehouseLatitude,
            warehouseLongitude,
            waypoints);

    Long totalDistance = routeService.getDistanceInMeters(optimizedDirections);
    Long totalDuration = routeService.getDurationInSeconds(optimizedDirections);
    List<String> poi = RouteUtils.extractAddressList(optimizedDirections);

    List<ShoppingEntity> orderedShoppingList =
        RouteUtils.reorderShoppingByOptimizedRoute(optimizedDirections, list);

    ShoppingRouteResponse routeResponse = new ShoppingRouteResponse();

    routeResponse.setPoi(poi);
    routeResponse.setTotalDistance(totalDistance);
    routeResponse.setTotalDurationSec(totalDuration);
    routeResponse.setTotalStops(orderedShoppingList.size());
    routeResponse.setTotalDuration(RouteUtils.formatSeconds(totalDuration));
    routeResponse.setTotalDistanceKm(Math.round(totalDistance / 1000.00) + " Km");
    routeResponse.setRouteDescription(
        RouteUtils.buildRouteDescription(orderedShoppingList, warehouse));

    return routeResponse;
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
