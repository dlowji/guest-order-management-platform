package com.dlowji.simple.command.api.data;

import com.dlowji.simple.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

    List<Order> findByTableId(String tableId);
}