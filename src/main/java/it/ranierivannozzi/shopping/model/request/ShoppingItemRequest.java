package it.ranierivannozzi.shopping.model.request;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoppingItemRequest {
  private String productName;
  private String productCode;
  private Integer quantity;
  private BigDecimal unitPrice;
  private String category;
  private String brand;
  private Double weight;
  private String unitOfMeasure;
}
