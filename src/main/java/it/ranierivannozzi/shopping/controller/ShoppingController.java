package it.ranierivannozzi.shopping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import it.ranierivannozzi.shopping.model.request.ShoppingRequest;
import it.ranierivannozzi.shopping.model.response.ShoppingResponse;
import it.ranierivannozzi.shopping.model.response.ShoppingRouteResponse;
import it.ranierivannozzi.shopping.openapi.ShoppingOpenApi;
import it.ranierivannozzi.shopping.route.ApiRoute;
import it.ranierivannozzi.shopping.route.ShoppingRoute;
import it.ranierivannozzi.shopping.service.IShoppingService;
import it.ranierivannozzi.shopping.utils.SortUtils;

@RestController
@RequestMapping(ApiRoute.V1 + ShoppingRoute.ROOT)
@Tag(name = ShoppingOpenApi.CONTROLLER_NAME, description = ShoppingOpenApi.CONTROLLER_DESCRIPTION)
public class ShoppingController {

  private final IShoppingService shoppingService;

  public ShoppingController(IShoppingService shoppingService) {
    this.shoppingService = shoppingService;
  }

  @Operation(
      summary = ShoppingOpenApi.CREATE_SUMMARY,
      description = ShoppingOpenApi.CREATE_DESCRIPTION,
      responses = {
        @ApiResponse(responseCode = "201", description = ShoppingOpenApi.CREATE_RESPONSE_201),
        @ApiResponse(responseCode = "400", description = ShoppingOpenApi.CREATE_RESPONSE_400)
      })
  @PostMapping
  public ResponseEntity<ShoppingResponse> createShopping(@RequestBody ShoppingRequest request) {
    ShoppingResponse saved = shoppingService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @Operation(
      summary = ShoppingOpenApi.UPDATE_SUMMARY,
      description = ShoppingOpenApi.UPDATE_DESCRIPTION,
      responses = {
        @ApiResponse(responseCode = "200", description = ShoppingOpenApi.UPDATE_RESPONSE_200),
        @ApiResponse(responseCode = "404", description = ShoppingOpenApi.UPDATE_RESPONSE_404)
      })
  @PatchMapping(ShoppingRoute.ID)
  public ResponseEntity<ShoppingResponse> updateShopping(
      @PathVariable Long id, @RequestBody ShoppingRequest partial) {
    ShoppingResponse updated = shoppingService.update(id, partial);
    return ResponseEntity.ok(updated);
  }

  @Operation(
      summary = ShoppingOpenApi.GET_ALL_SUMMARY,
      description = ShoppingOpenApi.GET_ALL_DESCRIPTION,
      responses = {
        @ApiResponse(responseCode = "200", description = ShoppingOpenApi.GET_ALL_RESPONSE_200)
      })
  @GetMapping
  public ResponseEntity<Page<ShoppingResponse>> getAllShopping(
      @RequestParam(defaultValue = "0")
          @Parameter(description = ShoppingOpenApi.PAGE_NUMBER_DESCRIPTION)
          int pageNumber,
      @RequestParam(defaultValue = "10")
          @Parameter(description = ShoppingOpenApi.PAGE_SIZE_DESCRIPTION)
          int pageSize,
      @RequestParam(required = false) @Parameter(description = ShoppingOpenApi.SORT_BY_DESCRIPTION)
          String[] sortBy,
      @RequestParam(defaultValue = "asc")
          @Parameter(description = ShoppingOpenApi.SORT_DIRECTION_DESCRIPTION)
          String[] sortDirection,
      @RequestParam(required = false) ShoppingStatus status) {
    Pageable pageable =
        PageRequest.of(pageNumber, pageSize, SortUtils.getSort(sortBy, sortDirection));
    Page<ShoppingResponse> list = shoppingService.findAll(pageable, status);
    return ResponseEntity.ok(list);
  }

  @Operation(
      summary = ShoppingOpenApi.GET_BY_ID_SUMMARY,
      description = ShoppingOpenApi.GET_BY_ID_DESCRIPTION,
      responses = {
        @ApiResponse(responseCode = "200", description = ShoppingOpenApi.GET_BY_ID_RESPONSE_200),
        @ApiResponse(responseCode = "404", description = ShoppingOpenApi.GET_BY_ID_RESPONSE_404)
      })
  @GetMapping(ShoppingRoute.ID)
  public ResponseEntity<ShoppingResponse> getShoppingById(@PathVariable Long id) {
    ShoppingResponse entity = shoppingService.findById(id);
    return ResponseEntity.ok(entity);
  }

  @Operation(
      summary = ShoppingOpenApi.DELETE_SUMMARY,
      description = ShoppingOpenApi.DELETE_DESCRIPTION,
      responses = {
        @ApiResponse(responseCode = "204", description = ShoppingOpenApi.DELETE_RESPONSE_204),
        @ApiResponse(responseCode = "404", description = ShoppingOpenApi.DELETE_RESPONSE_404)
      })
  @DeleteMapping(ShoppingRoute.ID)
  public ResponseEntity<Void> deleteShopping(@PathVariable Long id) {
    shoppingService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = ShoppingOpenApi.ROUTES_SUMMARY,
      description = ShoppingOpenApi.ROUTES_DESCRIPTION,
      responses = {
        @ApiResponse(responseCode = "200", description = ShoppingOpenApi.ROUTES_RESPONSE_200),
        @ApiResponse(responseCode = "404", description = ShoppingOpenApi.ROUTES_RESPONSE_404)
      })
  @GetMapping(ShoppingRoute.ROUTES)
  public ResponseEntity<List<ShoppingRouteResponse>> getShoppingRoutesByStatus(
      @RequestParam(required = false) ShoppingStatus status) {
    List<ShoppingRouteResponse> list = shoppingService.findRoutes(status);
    return ResponseEntity.ok(list);
  }
}
