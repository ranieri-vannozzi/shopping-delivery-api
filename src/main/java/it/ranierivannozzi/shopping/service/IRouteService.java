package it.ranierivannozzi.shopping.service;

import com.google.maps.model.DirectionsResult;
import java.util.Map;

public interface IRouteService {

  DirectionsResult getOptimizedRoute(
      Double warehouseLatitude,
      Double warehouseLongitude,
      Double deliveryLatitude,
      Double deliveryLongitude,
      Map<Double, Double> waypointsMap);

  Long getDistanceInMeters(DirectionsResult directions);

  Long getDurationInSeconds(DirectionsResult directions);
}
