package it.ranierivannozzi.shopping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import it.ranierivannozzi.shopping.model.request.ShoppingRequest;
import it.ranierivannozzi.shopping.model.response.ShoppingResponse;
import it.ranierivannozzi.shopping.model.response.ShoppingRouteResponse;
import it.ranierivannozzi.shopping.route.ApiRoute;
import it.ranierivannozzi.shopping.route.ShoppingRoute;
import it.ranierivannozzi.shopping.service.IShoppingService;

@RestController
@RequestMapping(ApiRoute.V1 + ShoppingRoute.ROOT)
@Tag(
    name = "Shopping Controller",
    description =
        "CRUD operations for managing shoppings, including route calculation for delivery")
public class ShoppingController {

  private final IShoppingService shoppingService;

  public ShoppingController(IShoppingService shoppingService) {
    this.shoppingService = shoppingService;
  }

  @Operation(
      summary = "Create a new shopping",
      description =
          "Creates a new shopping with given details such as delivery coordinates, items, and status",
      responses = {
        @ApiResponse(responseCode = "200", description = "Shopping created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request body")
      })
  @PostMapping
  public ResponseEntity<ShoppingResponse> createShopping(@RequestBody ShoppingRequest request) {
    ShoppingResponse saved = shoppingService.save(request);
    return ResponseEntity.ok(saved);
  }

  @Operation(
      summary = "Update certain fields of a shopping",
      description =
          "Allows partial updates of a shopping. Only specified fields in the request will be updated",
      responses = {
        @ApiResponse(responseCode = "200", description = "Shopping updated successfully"),
        @ApiResponse(responseCode = "404", description = "Shopping not found")
      })
  @PatchMapping(ShoppingRoute.ID)
  public ResponseEntity<ShoppingResponse> updateShopping(
      @PathVariable Long id, @RequestBody ShoppingRequest partial) {

    Optional<ShoppingResponse> optional = shoppingService.findById(id);
    if (optional.isEmpty()) return ResponseEntity.notFound().build();

    ShoppingResponse updated = shoppingService.save(partial);
    return ResponseEntity.ok(updated);
  }

  @Operation(
      summary = "Get all shoppings",
      description =
          "Returns a list of all shoppings. Can filter by status using the optional query parameter",
      responses = {@ApiResponse(responseCode = "200", description = "List of shoppings returned")})
  @GetMapping
  public ResponseEntity<List<ShoppingResponse>> getAllShopping(
      @RequestParam(required = false) ShoppingStatus status) {
    List<ShoppingResponse> list =
        (status != null) ? shoppingService.findByStatus(status) : shoppingService.findAll();
    return ResponseEntity.ok(list);
  }

  @Operation(
      summary = "Get a shopping by ID",
      description = "Returns a single shopping identified by its unique ID",
      responses = {
        @ApiResponse(responseCode = "200", description = "Shopping found"),
        @ApiResponse(responseCode = "404", description = "Shopping not found")
      })
  @GetMapping(ShoppingRoute.ID)
  public ResponseEntity<ShoppingResponse> getShoppingById(@PathVariable Long id) {
    return shoppingService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(
      summary = "Delete a shopping by ID",
      description = "Deletes a shopping identified by its ID",
      responses = {
        @ApiResponse(responseCode = "204", description = "Shopping deleted"),
        @ApiResponse(responseCode = "404", description = "Shopping not found")
      })
  @DeleteMapping(ShoppingRoute.ID)
  public ResponseEntity<Void> deleteShopping(@PathVariable Long id) {
    if (!shoppingService.existsById(id)) return ResponseEntity.notFound().build();
    shoppingService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "Get delivery routes by shopping status",
      description = "Calculates the delivery routes for all shoppings with the given status",
      responses = {
        @ApiResponse(responseCode = "200", description = "Routes returned successfully"),
        @ApiResponse(responseCode = "404", description = "No shoppings found for the given status")
      })
  @GetMapping(ShoppingRoute.ROUTES)
  public ResponseEntity<List<ShoppingRouteResponse>> getShoppingRoutesByStatus(
      @RequestParam(required = false) ShoppingStatus status) {
    List<ShoppingRouteResponse> list = shoppingService.findRoutes(status);
    return ResponseEntity.ok(list);
  }
}
