package org.example.uzumorderservice.order;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.uzumorderservice.common.service.GenericCrudService;
import org.example.uzumorderservice.notification.NotificationService;
import org.example.uzumorderservice.notification.dto.NotificationRequestDto;
import org.example.uzumorderservice.notification.dto.NotificationType;
import org.example.uzumorderservice.order.dto.OrderCreateDto;
import org.example.uzumorderservice.order.dto.OrderResponseDto;
import org.example.uzumorderservice.order.entity.Order;
import org.example.uzumorderservice.stock.StockFeignClient;
import org.example.uzumorderservice.stock.StockResponseDto;
import org.example.uzumorderservice.stock.StockUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class OrderService extends GenericCrudService<Order, UUID, OrderCreateDto, OrderCreateDto, OrderCreateDto, OrderResponseDto> {
    private final OrderRepository repository;
    private final Class<Order> EntityClass = Order.class;
    private final OrderDtoMapper mapper;
    private final StockFeignClient stockFeignClient;
    private final NotificationService notificationService;

    @Override
    @Transactional
    protected Order save(OrderCreateDto orderCreateDto) {

        String productId = orderCreateDto.getProductId();
        int orderQuantity = orderCreateDto.getCount();

        StockResponseDto stockResponseDto = stockFeignClient.getProduct(productId).getBody();

        if (stockResponseDto != null && stockResponseDto.getCount() >= orderQuantity) {
            Order entity = mapper.toEntity(orderCreateDto);
            Order savedOrder = repository.save(entity);

            StockUpdateDto updateRequest = new StockUpdateDto(productId, orderQuantity);
            ResponseEntity<String> updateResponse = stockFeignClient.updateStock(updateRequest);

            if (updateResponse.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Не удалось обновить инвентарь после создания заказа");
            }
            NotificationRequestDto createdOrder = new NotificationRequestDto("998997234425",
                    "created order", NotificationType.SMS);

            notificationService.sendSms(createdOrder);
            return savedOrder;

        } else {
            NotificationRequestDto createdOrder = new NotificationRequestDto("998997234425",
                    "created order failed", NotificationType.SMS);

            notificationService.sendSms(createdOrder);
            throw new IllegalArgumentException("Недостаточно товаров в инвентаре для создания заказа");
        }
    }


    @Override
    protected Order updateEntity(OrderCreateDto orderCreateDto, Order order) {
        return null;
    }
}
