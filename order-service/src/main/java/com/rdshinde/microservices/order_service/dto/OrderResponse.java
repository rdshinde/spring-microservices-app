package com.rdshinde.microservices.order_service.dto;

import java.math.BigDecimal;

public record OrderResponse(Long id, String orderNumber, String skuCode, BigDecimal Price, Integer quantity) {
}
