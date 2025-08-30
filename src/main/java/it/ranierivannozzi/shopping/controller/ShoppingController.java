package it.ranierivannozzi.shopping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import it.ranierivannozzi.shopping.model.request.ShoppingRequest;
import it.ranierivannozzi.shopping.model.response.ShoppingResponse;
import it.ranierivannozzi.shopping.model.response.ShoppingRouteResponse;
import it.ranierivannozzi.shopping.service.IShoppingService;

@RestController
@RequestMapping("/api/shoppings")
@Tag(
    name = "ShoppingRequest Controller",
    description = "CRUD operations for ShoppingRequest entity")
public class ShoppingController {

  private final IShoppingService shoppingService;

  public ShoppingController(IShoppingService shoppingService) {
    this.shoppingService = shoppingService;
  }

  @Operation(summary = "Create a new shopping")
  @PostMapping
  public ResponseEntity<ShoppingResponse> createShopping(@RequestBody ShoppingRequest request) {
    ShoppingResponse saved = shoppingService.save(request);
    return ResponseEntity.ok(saved);
  }

  @Operation(summary = "Update certain fields of a shopping")
  @PatchMapping("/{id}")
  public ResponseEntity<ShoppingResponse> updateShopping(
      @PathVariable Long id, @RequestBody ShoppingRequest partial) {

    Optional<ShoppingResponse> optional = shoppingService.findById(id);
    if (optional.isEmpty()) return ResponseEntity.notFound().build();

    ShoppingRequest request = new ShoppingRequest();
    //        if (partial.getLatitude() != 0) request.setLatitude(partial.getLatitude());
    //        if (partial.getLongitude() != 0) request.setLongitude(partial.getLongitude());
    //        if (partial.getItems() != null) request.setItems(partial.getItems());
    //        if (partial.getStatus() != null) request.setStatus(partial.getStatus());

    ShoppingResponse updated = shoppingService.save(request);
    return ResponseEntity.ok(updated);
  }

  @Operation(summary = "Get all shoppings, optionally filter by status")
  @GetMapping
  public ResponseEntity<List<ShoppingResponse>> getAllShopping(
      @RequestParam(required = false) ShoppingStatus status) {
    List<ShoppingResponse> list =
        (status != null) ? shoppingService.findByStatus(status) : shoppingService.findAll();
    return ResponseEntity.ok(list);
  }

  @Operation(summary = "Get a single ShoppingRequest by ID")
  @GetMapping("/{id}")
  public ResponseEntity<ShoppingResponse> getShoppingById(@PathVariable Long id) {
    return shoppingService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Delete a ShoppingRequest by ID")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteShopping(@PathVariable Long id) {
    if (!shoppingService.existsById(id)) return ResponseEntity.notFound().build();
    shoppingService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Get a single ShoppingRequest by ID")
  @GetMapping("/{status}")
  public ResponseEntity<List<ShoppingRouteResponse>> getShoppingById(
      @PathVariable ShoppingStatus status) {
    List<ShoppingRouteResponse> list = shoppingService.findRoutes(status);
    return ResponseEntity.ok(list);
  }
}
