package it.ranierivannozzi.shopping.enumeration;

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

  public String getDisplayName() {
    return displayName;
  }
}
