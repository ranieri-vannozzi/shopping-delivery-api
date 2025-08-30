package it.ranierivannozzi.shopping.model.response;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShoppingItemResponse extends BaseResponse {
  private Long id;
  private String productName;
  private String productCode;
  private Integer quantity;
  private BigDecimal unitPrice;
  private BigDecimal totalPrice;
  private String category;
  private String brand;
  private Double weight;
  private String unitOfMeasure;
}
