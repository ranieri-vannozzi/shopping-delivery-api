package it.ranierivannozzi.shopping.utils;

import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import it.ranierivannozzi.shopping.entity.ShoppingEntity;
import it.ranierivannozzi.shopping.entity.WarehouseEntity;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RouteUtils {

  private static final String ARROW = " --> ";

  private RouteUtils() {}

  /**
   * Formats a duration given in seconds into a string with hours, minutes, and seconds.
   *
   * @param seconds the total duration in seconds to be formatted
   * @return a formatted string in the format "hh:mm:ss", where hh represents hours, mm represents
   *     minutes, and ss represents seconds
   */
  public static String formatSeconds(long seconds) {
    Duration d = Duration.ofSeconds(seconds);
    return String.format(
        "%02dh %02dm %02ds", d.toHoursPart(), d.toMinutesPart(), d.toSecondsPart());
  }

  /**
   * Extracts a list of unique start addresses from the given directions result. The first start
   * address is duplicated and appended to the end of the list.
   *
   * @param directionsResult the result from the Directions API containing route information,
   *     including start addresses of route legs
   * @return a list of start addresses derived from the provided directions result, or an empty list
   *     if the input is null or contains no valid addresses
   */
  public static List<String> extractAddressList(DirectionsResult directionsResult) {
    if (directionsResult == null || directionsResult.routes == null) {
      return Collections.emptyList();
    }

    List<String> startAddresses = new ArrayList<>();

    for (DirectionsRoute route : directionsResult.routes) {
      if (route.legs != null) {
        for (DirectionsLeg leg : route.legs) {
          if (Objects.nonNull(leg.startAddress) && !leg.startAddress.isBlank()) {
            startAddresses.add(leg.startAddress.trim());
          }
        }
      }
    }

    if (!startAddresses.isEmpty()) {
      startAddresses.add(startAddresses.get(0));
    }

    return startAddresses;
  }

  /**
   * Constructs a description for an optimized delivery route starting and ending at a warehouse,
   * with intermediate deliveries to customers.
   *
   * @param orderedList a list of ShoppingEntity objects representing the deliveries in a specific
   *     order
   * @param warehouse a WarehouseEntity object representing the starting and ending point of the
   *     route
   * @return a String describing the optimized route, starting and ending at the warehouse, and
   *     passing through each delivery in the specified order
   */
  public static String buildRouteDescription(
      List<ShoppingEntity> orderedList, WarehouseEntity warehouse) {

    StringBuilder description = new StringBuilder("Optimized route: ");
    String warehouseName = warehouse.getName();

    description.append("Warehouse (").append(warehouseName).append(")");

    for (int i = 0; i < orderedList.size(); i++) {
      ShoppingEntity shopping = orderedList.get(i);

      description.append(ARROW).append("Delivery ").append(i + 1);

      if (shopping.getCustomerName() != null && !shopping.getCustomerName().isBlank()) {
        description.append(" (").append(shopping.getCustomerName()).append(")");
      }
    }

    description.append(ARROW).append("Warehouse (").append(warehouseName).append(")");

    return description.toString();
  }

  /**
   * Reorders a list of shopping entities based on an optimized route provided by a
   * DirectionsResult. If the optimized route contains a valid waypoint order, the shopping entities
   * in the original list will be reordered accordingly. If no valid waypoint order is present, the
   * original list is returned as-is.
   *
   * @param optimizedDirections a DirectionsResult object containing route optimization details,
   *     including waypoint order
   * @param originalList the original list of ShoppingEntity objects to be reordered
   * @return a reordered list of ShoppingEntity objects based on the waypoint order, or the original
   *     list if no valid order is found
   */
  public static List<ShoppingEntity> reorderShoppingByOptimizedRoute(
      DirectionsResult optimizedDirections, List<ShoppingEntity> originalList) {

    if (optimizedDirections.routes.length > 0
        && optimizedDirections.routes[0].waypointOrder != null) {

      int[] waypointOrder = optimizedDirections.routes[0].waypointOrder;
      List<ShoppingEntity> reorderedList = new ArrayList<>(waypointOrder.length);

      for (int index : waypointOrder) {
        if (index >= 0 && index < originalList.size()) {
          reorderedList.add(originalList.get(index));
        }
      }
      return reorderedList;
    }

    return new ArrayList<>(originalList);
  }
}
