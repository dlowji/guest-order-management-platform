package com.dlowji.simple.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, String> {

}