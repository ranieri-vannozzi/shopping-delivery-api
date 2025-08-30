package it.ranierivannozzi.shopping.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

public class SortUtils {

  private SortUtils() {
    // Utility class non instanziabile
  }

  public static Sort getSort(String[] sortBy, String[] sortDirection) {
    if (sortBy != null && sortBy.length > 0) {
      List<Sort.Order> orders = new ArrayList<>();
      for (int i = 0; i < sortBy.length; i++) {
        Sort.Direction direction = Sort.Direction.ASC; // default
        if (sortDirection != null && i < sortDirection.length) {
          if ("desc".equalsIgnoreCase(sortDirection[i])) {
            direction = Sort.Direction.DESC;
          }
        }
        orders.add(new Sort.Order(direction, sortBy[i]));
      }
      return Sort.by(orders);
    } else {
      return Sort.unsorted();
    }
  }
}
