package it.ranierivannozzi.shopping.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;

@Getter
@Setter
@Entity
@Table(name = "shopping")
public class ShoppingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "delivery_latitude")
  private Double deliveryLatitude;

  @NotNull
  @Column(name = "delivery_longitude")
  private Double deliveryLongitude;

  @Column(name = "delivery_address")
  private String deliveryAddress;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private ShoppingStatus status;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "requested_delivery_date")
  private LocalDateTime requestedDeliveryDate;

  @Column(name = "customer_name")
  private String customerName;

  @Column(name = "customer_phone")
  private String customerPhone;

  @Column(name = "delivery_notes")
  private String deliveryNotes;

  @Column(name = "total_amount")
  private Double totalAmount;

  @OneToMany(mappedBy = "shopping", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ProductEntity> items;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "warehouse_id")
  private WarehouseEntity warehouse;

  public ShoppingEntity() {}

  public ShoppingEntity(
      Double deliveryLatitude,
      Double deliveryLongitude,
      String deliveryAddress,
      String customerName) {
    this.deliveryLatitude = deliveryLatitude;
    this.deliveryLongitude = deliveryLongitude;
    this.deliveryAddress = deliveryAddress;
    this.customerName = customerName;
    this.status = ShoppingStatus.CREATED;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  /*
   Standard getter and setter handled by lombok;
  */

  public void setDeliveryAddress(String deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
    this.updatedAt = LocalDateTime.now();
  }

  public void setStatus(ShoppingStatus status) {
    this.status = status;
    this.updatedAt = LocalDateTime.now();
  }

  public void setRequestedDeliveryDate(LocalDateTime requestedDeliveryDate) {
    this.requestedDeliveryDate = requestedDeliveryDate;
    this.updatedAt = LocalDateTime.now();
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
    this.updatedAt = LocalDateTime.now();
  }

  public void setDeliveryNotes(String deliveryNotes) {
    this.deliveryNotes = deliveryNotes;
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}
