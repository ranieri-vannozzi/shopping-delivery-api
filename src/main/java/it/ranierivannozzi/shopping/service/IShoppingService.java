package it.ranierivannozzi.shopping.service;

import java.util.List;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import it.ranierivannozzi.shopping.model.request.ShoppingRequest;
import it.ranierivannozzi.shopping.model.response.ShoppingResponse;
import it.ranierivannozzi.shopping.model.response.ShoppingRouteResponse;

public interface IShoppingService {

  /**
   * Save a new Shopping or update an existing one.
   *
   * @param request the shopping request
   * @return the saved shopping response
   */
  ShoppingResponse save(ShoppingRequest request);

  /**
   * Update an existing shopping.
   *
   * @param request the shopping request
   * @return the updated shopping response
   */
  ShoppingResponse update(Long id, ShoppingRequest request);

  /**
   * Find shopping by ID.
   *
   * @param id the shopping ID
   * @return Optional containing the shopping if found
   */
  ShoppingResponse findById(Long id);

  /**
   * Retrieve all shoppings.
   *
   * @return list of all shopping responses
   */
  List<ShoppingResponse> findAll(ShoppingStatus status);

  /**
   * Delete shopping by ID.
   *
   * @param id the shopping ID
   */
  void deleteById(Long id);

  /**
   * Check if shopping exists by ID.
   *
   * @param id the shopping ID
   * @return true if exists, false otherwise
   */
  boolean existsById(Long id);

  /**
   * Finds and retrieves shopping routes based on the specified shopping status.
   *
   * @param status the shopping status used to filter the routes
   * @return a list of shopping route responses that match the given status
   */
  List<ShoppingRouteResponse> findRoutes(ShoppingStatus status);
}
