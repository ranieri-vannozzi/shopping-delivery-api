package it.ranierivannozzi.shopping.model.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;

@Data
@NoArgsConstructor
public class ShoppingResponse {
  private Long id;
  private Double deliveryLatitude;
  private Double deliveryLongitude;
  private String deliveryAddress;
  private ShoppingStatus status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime requestedDeliveryDate;
  private String customerName;
  private String customerPhone;
  private String deliveryNotes;
  private Double totalAmount;
  private Long warehouseId;
  private List<ShoppingItemResponse> items;
}
