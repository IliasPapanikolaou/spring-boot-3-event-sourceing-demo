package com.ipap.commonlibs.dto;

public record OrderRequest(
        String orderId,
        String userId,
        String name,
        int qty,
        double price
) {
}
