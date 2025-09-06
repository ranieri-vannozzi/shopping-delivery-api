package it.ranierivannozzi.shopping.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "warehouse")
public class WarehouseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "name")
  private String name;

  @NotNull
  @Column(name = "latitude")
  private Double latitude;

  @NotNull
  @Column(name = "longitude")
  private Double longitude;

  @Column(name = "address")
  private String address;

  @Column(name = "phone")
  private String phone;

  @Column(name = "is_active")
  private Boolean isActive;

  @OneToMany(mappedBy = "warehouse", fetch = FetchType.LAZY)
  private List<ShoppingEntity> shopping;

  public WarehouseEntity() {}

  public WarehouseEntity(String name, Double latitude, Double longitude) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.isActive = true;
  }

  /*
   Standard getter and setter handled by lombok;
  */

}
