package com.dlowji.simple.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {
}
