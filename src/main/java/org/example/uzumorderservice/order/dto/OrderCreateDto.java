package org.example.uzumorderservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreateDto {
    private String productId;
    private Integer count;
}
