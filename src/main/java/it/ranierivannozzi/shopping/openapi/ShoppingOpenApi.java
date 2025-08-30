package it.ranierivannozzi.shopping.openapi;

public class ShoppingOpenApi {

  public static final String CONTROLLER_NAME = "Shopping Controller";
  public static final String CONTROLLER_DESCRIPTION =
      "CRUD operations for managing shopping items, including route calculation for delivery";

  public static final String CREATE_SUMMARY = "Create a new shopping";
  public static final String CREATE_DESCRIPTION =
      "Creates a new shopping with given details such as delivery coordinates, items, and status";
  public static final String CREATE_RESPONSE_201 = "Shopping created successfully";
  public static final String CREATE_RESPONSE_400 = "Invalid request body";

  public static final String UPDATE_SUMMARY = "Update certain fields of a shopping";
  public static final String UPDATE_DESCRIPTION =
      "Allows partial updates of a shopping. Only specified fields in the request will be updated";
  public static final String UPDATE_RESPONSE_200 = "Shopping updated successfully";
  public static final String UPDATE_RESPONSE_404 = "Shopping not found";

  public static final String GET_ALL_SUMMARY = "Get all shopping items";
  public static final String GET_ALL_DESCRIPTION =
      "Returns a list of all shopping items. Can filter by status using the optional query parameter";
  public static final String GET_ALL_RESPONSE_200 = "List of shopping items returned";

  public static final String GET_BY_ID_SUMMARY = "Get a shopping by ID";
  public static final String GET_BY_ID_DESCRIPTION =
      "Returns a single shopping identified by its unique ID";
  public static final String GET_BY_ID_RESPONSE_200 = "Shopping found";
  public static final String GET_BY_ID_RESPONSE_404 = "Shopping not found";

  public static final String DELETE_SUMMARY = "Delete a shopping by ID";
  public static final String DELETE_DESCRIPTION = "Deletes a shopping identified by its ID";
  public static final String DELETE_RESPONSE_204 = "Shopping deleted";
  public static final String DELETE_RESPONSE_404 = "Shopping not found";

  public static final String ROUTES_SUMMARY = "Get delivery routes by shopping status";
  public static final String ROUTES_DESCRIPTION =
      "Calculates the delivery routes for all shopping items with the given status";
  public static final String ROUTES_RESPONSE_200 = "Routes returned successfully";
  public static final String ROUTES_RESPONSE_404 = "No shopping items found for the given status";

  private ShoppingOpenApi() {}
}
