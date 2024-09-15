package com.ipap.commonlibs.dto;

import com.ipap.commonlibs.dao.OrderStatus;

public record OrderResponse (
        String orderId,
        OrderStatus status
) {
}
