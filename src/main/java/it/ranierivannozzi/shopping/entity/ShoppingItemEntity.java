package it.ranierivannozzi.shopping.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "shopping_item")
public class ShoppingItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "product_name")
  private String productName;

  @Column(name = "product_code")
  private String productCode;

  @NotNull
  @Column(name = "quantity")
  private Integer quantity;

  @NotNull
  @Column(name = "unit_price")
  private BigDecimal unitPrice;

  @Column(name = "total_price")
  private BigDecimal totalPrice;

  @Column(name = "category")
  private String category;

  @Column(name = "brand")
  private String brand;

  @Column(name = "weight")
  private Double weight;

  @Column(name = "unit_of_measure")
  private String unitOfMeasure;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shopping_id")
  private ShoppingEntity shopping;

  public ShoppingItemEntity() {}

  public ShoppingItemEntity(String productName, Integer quantity, BigDecimal unitPrice) {
    this.productName = productName;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
  }

  /*
   Standard getter and setter handled by lombok;
  */

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
    updateTotalPrice();
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
    updateTotalPrice();
  }

  private void updateTotalPrice() {
    if (unitPrice != null && quantity != null) {
      this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
  }

  @PrePersist
  @PreUpdate
  public void calculateTotalPrice() {
    updateTotalPrice();
  }
}
