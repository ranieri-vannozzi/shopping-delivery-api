package it.ranierivannozzi.shopping.utils;

import it.ranierivannozzi.shopping.entity.ShoppingEntity;
import it.ranierivannozzi.shopping.entity.ShoppingItemEntity;
import it.ranierivannozzi.shopping.entity.WarehouseEntity;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import it.ranierivannozzi.shopping.model.request.ShoppingItemRequest;
import it.ranierivannozzi.shopping.model.request.ShoppingRequest;
import it.ranierivannozzi.shopping.model.response.ShoppingItemResponse;
import it.ranierivannozzi.shopping.model.response.ShoppingResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingParser {

  private ShoppingParser() {}

  public static ShoppingEntity toEntity(ShoppingRequest request, WarehouseEntity warehouse) {
    ShoppingEntity shopping = new ShoppingEntity();
    shopping.setDeliveryLatitude(request.getDeliveryLatitude());
    shopping.setDeliveryLongitude(request.getDeliveryLongitude());
    shopping.setDeliveryAddress(request.getDeliveryAddress());
    shopping.setStatus(ShoppingStatus.CREATED);
    shopping.setRequestedDeliveryDate(request.getRequestedDeliveryDate());
    shopping.setCustomerName(request.getCustomerName());
    shopping.setCustomerPhone(request.getCustomerPhone());
    shopping.setDeliveryNotes(request.getDeliveryNotes());
    shopping.setTotalAmount(request.getTotalAmount());
    shopping.setWarehouse(warehouse);

    if (request.getItems() != null) {
      List<ShoppingItemEntity> items =
          request.getItems().stream()
              .map(itemReq -> toEntity(itemReq, shopping))
              .collect(Collectors.toList());
      shopping.setItems(items);
    }

    return shopping;
  }

  public static ShoppingItemEntity toEntity(ShoppingItemRequest request, ShoppingEntity shopping) {
    ShoppingItemEntity item = new ShoppingItemEntity();
    item.setProductName(request.getProductName());
    item.setProductCode(request.getProductCode());
    item.setQuantity(request.getQuantity());
    item.setUnitPrice(request.getUnitPrice());
    item.setCategory(request.getCategory());
    item.setBrand(request.getBrand());
    item.setWeight(request.getWeight());
    item.setUnitOfMeasure(request.getUnitOfMeasure());
    item.setShopping(shopping);
    return item;
  }

  public static ShoppingResponse toResponse(ShoppingEntity entity, Boolean includeItems) {
    ShoppingResponse response = new ShoppingResponse();
    response.setId(entity.getId());
    response.setDeliveryLatitude(entity.getDeliveryLatitude());
    response.setDeliveryLongitude(entity.getDeliveryLongitude());
    response.setDeliveryAddress(entity.getDeliveryAddress());
    response.setStatus(entity.getStatus());
    response.setCreatedAt(entity.getCreatedAt());
    response.setUpdatedAt(entity.getUpdatedAt());
    response.setRequestedDeliveryDate(entity.getRequestedDeliveryDate());
    response.setCustomerName(entity.getCustomerName());
    response.setCustomerPhone(entity.getCustomerPhone());
    response.setDeliveryNotes(entity.getDeliveryNotes());
    response.setTotalAmount(entity.getTotalAmount());
    response.setWarehouseId(entity.getWarehouse() != null ? entity.getWarehouse().getId() : null);

    if (includeItems && entity.getItems() != null) {
      response.setItems(
          entity.getItems().stream()
              .map(
                  item -> {
                    ShoppingItemResponse p = new ShoppingItemResponse();
                    p.setId(item.getId());
                    p.setProductName(item.getProductName());
                    p.setProductCode(item.getProductCode());
                    p.setQuantity(item.getQuantity());
                    p.setUnitPrice(item.getUnitPrice());
                    p.setTotalPrice(item.getTotalPrice());
                    p.setCategory(item.getCategory());
                    p.setBrand(item.getBrand());
                    p.setWeight(item.getWeight());
                    p.setUnitOfMeasure(item.getUnitOfMeasure());
                    return p;
                  })
              .collect(Collectors.toList()));
    }

    return response;
  }
}
