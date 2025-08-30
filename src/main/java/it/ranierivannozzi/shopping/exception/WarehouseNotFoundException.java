package it.ranierivannozzi.shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WarehouseNotFoundException extends RuntimeException {
  public WarehouseNotFoundException(Long id) {
    super("Warehouse with id " + id + " not found");
  }
}
