package it.ranierivannozzi.shopping.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import it.ranierivannozzi.shopping.entity.ShoppingEntity;
import it.ranierivannozzi.shopping.entity.WarehouseEntity;
import it.ranierivannozzi.shopping.enumeration.ShoppingStatus;
import it.ranierivannozzi.shopping.exception.ShoppingNotEditableException;
import it.ranierivannozzi.shopping.exception.ShoppingNotFoundException;
import it.ranierivannozzi.shopping.exception.WarehouseNotFoundException;
import it.ranierivannozzi.shopping.model.request.ShoppingRequest;
import it.ranierivannozzi.shopping.model.response.ShoppingResponse;
import it.ranierivannozzi.shopping.repository.ShoppingRepository;
import it.ranierivannozzi.shopping.repository.WarehouseRepository;

class ShoppingServiceTest {

  private ShoppingRepository shoppingRepository;
  private WarehouseRepository warehouseRepository;
  private ShoppingService shoppingService;

  @BeforeEach
  void setUp() {
    shoppingRepository = mock(ShoppingRepository.class);
    warehouseRepository = mock(WarehouseRepository.class);

    shoppingService =
        new ShoppingService(shoppingRepository, warehouseRepository, mock(RouteService.class));
  }

  @Test
  void save_ShouldPersistShoppingAndReturnResponse() {

    ShoppingRequest request = new ShoppingRequest();
    request.setWarehouseId(1L);

    WarehouseEntity warehouse = new WarehouseEntity();
    warehouse.setId(1L);

    ShoppingEntity savedEntity = new ShoppingEntity();
    savedEntity.setId(100L);

    when(warehouseRepository.findById(1L)).thenReturn(Optional.of(warehouse));
    when(shoppingRepository.save(any())).thenReturn(savedEntity);

    ShoppingResponse response = shoppingService.save(request);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo(100L);
    verify(shoppingRepository, times(1)).save(any(ShoppingEntity.class));
  }

  @Test
  void save_ShouldThrow_WhenWarehouseNotFound() {
    ShoppingRequest request = new ShoppingRequest();
    request.setWarehouseId(99L);

    when(warehouseRepository.findById(99L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> shoppingService.save(request))
        .isInstanceOf(WarehouseNotFoundException.class);
  }

  @Test
  void update_ShouldUpdateEditableShopping() {
    ShoppingRequest request = new ShoppingRequest();
    request.setCustomerName("New Name");

    ShoppingEntity entity = new ShoppingEntity();
    entity.setId(5L);
    entity.setStatus(ShoppingStatus.CREATED);

    when(shoppingRepository.findById(5L)).thenReturn(Optional.of(entity));
    when(shoppingRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

    ShoppingResponse response = shoppingService.update(5L, request);

    assertThat(response).isNotNull();
    assertThat(response.getCustomerName()).isEqualTo("New Name");
  }

  @Test
  void update_ShouldThrow_WhenShoppingNotEditable() {
    ShoppingRequest request = new ShoppingRequest();

    ShoppingEntity entity = new ShoppingEntity();
    entity.setId(5L);
    entity.setStatus(ShoppingStatus.DELIVERED);

    when(shoppingRepository.findById(5L)).thenReturn(Optional.of(entity));

    assertThatThrownBy(() -> shoppingService.update(5L, request))
        .isInstanceOf(ShoppingNotEditableException.class);
  }

  @Test
  void findById_ShouldReturnShoppingResponse() {
    ShoppingEntity entity = new ShoppingEntity();
    entity.setId(10L);

    when(shoppingRepository.findById(10L)).thenReturn(Optional.of(entity));

    ShoppingResponse response = shoppingService.findById(10L);

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo(10L);
  }

  @Test
  void findById_ShouldThrow_WhenNotFound() {
    when(shoppingRepository.findById(10L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> shoppingService.findById(10L))
        .isInstanceOf(ShoppingNotFoundException.class);
  }

  @Test
  void findAll_ShouldReturnPageOfResponses() {
    ShoppingEntity e1 = new ShoppingEntity();
    e1.setId(1L);

    Page<ShoppingEntity> page = new PageImpl<>(List.of(e1));

    when(shoppingRepository.findAll(any(Pageable.class))).thenReturn(page);

    Page<ShoppingResponse> result = shoppingService.findAll(Pageable.unpaged(), null);

    assertThat(result.getTotalElements()).isEqualTo(1);
    assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
  }

  @Test
  void deleteById_ShouldDeleteEntity() {
    ShoppingEntity entity = new ShoppingEntity();
    entity.setId(123L);

    when(shoppingRepository.findById(123L)).thenReturn(Optional.of(entity));

    shoppingService.deleteById(123L);

    verify(shoppingRepository, times(1)).delete(entity);
  }

  @Test
  void deleteById_ShouldThrow_WhenNotFound() {
    when(shoppingRepository.findById(123L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> shoppingService.deleteById(123L))
        .isInstanceOf(ShoppingNotFoundException.class);
  }
}
