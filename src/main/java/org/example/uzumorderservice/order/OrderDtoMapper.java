package org.example.uzumorderservice.order;

import lombok.RequiredArgsConstructor;
import org.example.uzumorderservice.common.service.GenericDtoMapper;
import org.example.uzumorderservice.order.dto.OrderCreateDto;
import org.example.uzumorderservice.order.dto.OrderResponseDto;
import org.example.uzumorderservice.order.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDtoMapper extends GenericDtoMapper<Order, OrderCreateDto, OrderCreateDto, OrderResponseDto> {

    private final ModelMapper mapper;

    @Override
    public Order toEntity(OrderCreateDto orderCreateDto) {
        return mapper.map(orderCreateDto, Order.class);
    }

    @Override
    public OrderResponseDto toResponseDto(Order order) {
        return mapper.map(order, OrderResponseDto.class);
    }

    @Override
    public void update(OrderCreateDto orderCreateDto, Order order) {
        mapper.map(orderCreateDto, order);
    }
}
