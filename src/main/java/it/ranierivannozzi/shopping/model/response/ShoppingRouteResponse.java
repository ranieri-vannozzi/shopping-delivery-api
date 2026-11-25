package it.ranierivannozzi.shopping.model.response;

import it.ranierivannozzi.shopping.entity.ShoppingEntity;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ShoppingRouteResponse extends BaseResponse {

  private Integer deliveryOrder;
  private Integer totalStops;
  private String totalDuration;
  private Long totalDurationSec;

  private Long totalDistance;
  private String totalDistanceKm;

  private Long PartialDistance;

  private String routeDescription;

  private ShoppingEntity shopping;

  private List<String> poi;
}
