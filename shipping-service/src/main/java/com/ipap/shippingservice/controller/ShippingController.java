package com.ipap.shippingservice.controller;

import com.ipap.shippingservice.service.ShippingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shipping")
public class ShippingController {

    private final ShippingEventService shippingEventService;

    @PostMapping("/{orderId}/ship")
    public ResponseEntity<String> shipOrder(@PathVariable String orderId) {
        shippingEventService.shipOrder(orderId);
        return ResponseEntity.ok("Order shipped successfully.");
    }

    @PostMapping("/{orderId}/deliver")
    public ResponseEntity<String> deliverOrder(@PathVariable String orderId) {
        shippingEventService.deliverOrder(orderId);
        return ResponseEntity.ok("Order delivered successfully.");
    }
}
