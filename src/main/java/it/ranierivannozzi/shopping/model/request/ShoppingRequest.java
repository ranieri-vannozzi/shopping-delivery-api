package it.ranierivannozzi.shopping.model.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoppingRequest {

  private Double deliveryLatitude;
  private Double deliveryLongitude;
  private String deliveryAddress;
  private LocalDateTime requestedDeliveryDate;
  private String customerName;
  private String customerPhone;
  private String deliveryNotes;
  private Double totalAmount;
  private Long warehouseId;
  private List<ShoppingItemRequest> items;
}
