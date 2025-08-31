package it.ranierivannozzi.shopping.interceptor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import it.ranierivannozzi.shopping.exception.RouteRuntimeException;
import it.ranierivannozzi.shopping.exception.ShoppingNotEditableException;
import it.ranierivannozzi.shopping.exception.ShoppingNotFoundException;
import it.ranierivannozzi.shopping.exception.WarehouseNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", message);
    return new ResponseEntity<>(body, status);
  }

  @ExceptionHandler(WarehouseNotFoundException.class)
  public ResponseEntity<Object> handleWarehouseNotFound(WarehouseNotFoundException ex) {
    return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(ShoppingNotFoundException.class)
  public ResponseEntity<Object> handleShoppingNotFound(ShoppingNotFoundException ex) {
    return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(ShoppingNotEditableException.class)
  public ResponseEntity<Object> handleShoppingNotEditable(ShoppingNotEditableException ex) {
    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(RouteRuntimeException.class)
  public ResponseEntity<Object> handleRouteRuntime(RouteRuntimeException ex) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGeneric(Exception ex) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage());
  }
}
