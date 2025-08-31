package it.ranierivannozzi.shopping.service;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import it.ranierivannozzi.shopping.exception.RouteRuntimeException;

@Service
public class RouteService implements IRouteService {

  private final GeoApiContext context;

  public RouteService(@Value("${google.maps.api.key}") String apiKey) {
    context = new GeoApiContext.Builder().apiKey(apiKey).build();
  }

  public DirectionsResult getOptimizedRoute(
      Double startLatitude,
      Double startLongitude,
      Double destinationLatitude,
      Double destinationLongitude,
      Map<Double, Double> waypointsMap) {

    String origin = startLatitude + "," + startLongitude;
    String destination = destinationLatitude + "," + destinationLongitude;
    String[] waypoints = convertMapToWaypoints(waypointsMap);

    try {
      return DirectionsApi.newRequest(context)
          .origin(origin)
          .destination(destination)
          .waypoints(waypoints)
          .mode(TravelMode.DRIVING)
          .optimizeWaypoints(true)
          .await();

    } catch (Exception e) {
      throw new RouteRuntimeException(origin, destination);
    }
  }

  private String[] convertMapToWaypoints(Map<Double, Double> waypointsMap) {
    if (waypointsMap == null || waypointsMap.isEmpty()) {
      return new String[0];
    }

    List<String> waypoints = new ArrayList<>();

    for (Map.Entry<Double, Double> entry : waypointsMap.entrySet()) {
      Double latitude = entry.getKey();
      Double longitude = entry.getValue();

      if (latitude != null && longitude != null) {
        waypoints.add(latitude + "," + longitude);
      }
    }

    return waypoints.toArray(new String[0]);
  }

  @Override
  public Long getDistanceInMeters(DirectionsResult directionsResult) {
    if (directionsResult.routes.length == 0) {
      return 0L;
    }

    long totalDistance = 0;
    for (DirectionsLeg leg : directionsResult.routes[0].legs) {
      totalDistance += leg.distance.inMeters;
    }

    return totalDistance;
  }

  @Override
  public Long getDurationInSeconds(DirectionsResult directionsResult) {
    if (directionsResult.routes.length == 0) {
      return 0L;
    }

    long totalDuration = 0;
    for (DirectionsLeg leg : directionsResult.routes[0].legs) {
      totalDuration += leg.duration.inSeconds;
    }

    return totalDuration;
  }
}
