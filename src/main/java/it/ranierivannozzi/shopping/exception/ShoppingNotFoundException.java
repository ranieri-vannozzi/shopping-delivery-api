package it.ranierivannozzi.shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShoppingNotFoundException extends RuntimeException {
  public ShoppingNotFoundException(Long id) {
    super("Shopping with id " + id + " not found");
  }
}
