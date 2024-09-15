package com.ipap.shippingservice.repository;

import com.ipap.commonlibs.dao.OrderEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderEventRepository extends MongoRepository<OrderEvent, String> {
}
