package it.ranierivannozzi.shopping.enumeration;

import java.util.Map;
import java.util.Set;
import lombok.Getter;

@Getter
public enum ShoppingStatus {
  CREATED("Created"),
  CONFIRMED("Confirmed"),
  PREPARING("Preparing"),
  READY_FOR_DELIVERY("Ready for delivery"),
  IN_TRANSIT("In transit"),
  DELIVERED("Delivered"),
  CANCELLED("Cancelled");

  private final String displayName;

  ShoppingStatus(String displayName) {
    this.displayName = displayName;
  }

  private static final Map<ShoppingStatus, Set<ShoppingStatus>> allowedTransitions =
      Map.of(
          CREATED, Set.of(CONFIRMED),
          CONFIRMED, Set.of(PREPARING),
          PREPARING, Set.of(READY_FOR_DELIVERY),
          READY_FOR_DELIVERY, Set.of(IN_TRANSIT),
          IN_TRANSIT, Set.of(DELIVERED),
          DELIVERED, Set.of(CANCELLED),
          CANCELLED, Set.of());

  public boolean isTransitionAllowed(ShoppingStatus nextStatus) {
    return allowedTransitions.getOrDefault(this, Set.of()).contains(nextStatus);
  }

  public boolean isEditable() {
    return switch (this) {
      case CREATED, CONFIRMED, PREPARING, READY_FOR_DELIVERY -> true;
      case IN_TRANSIT, DELIVERED, CANCELLED -> false;
    };
  }
}
