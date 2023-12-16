package org.example.uzumorderservice.order;

import org.example.uzumorderservice.common.repository.GenericSpecificationRepository;
import org.example.uzumorderservice.order.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends GenericSpecificationRepository<Order, UUID> {
}
