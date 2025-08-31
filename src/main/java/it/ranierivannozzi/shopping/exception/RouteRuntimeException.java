package it.ranierivannozzi.shopping.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RouteRuntimeException extends RuntimeException {
  public RouteRuntimeException(String origin, String destination) {
    super("Route from " + origin + " to " + destination + " not found");
  }
}
