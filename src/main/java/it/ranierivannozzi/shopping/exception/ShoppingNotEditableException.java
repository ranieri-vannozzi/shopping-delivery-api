package it.ranierivannozzi.shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ShoppingNotEditableException extends RuntimeException {
  public ShoppingNotEditableException(Long id) {
    super(
        "Shopping with id "
            + id
            + " cannot be modified because it has already left the warehouse: "
            + id);
  }
}
