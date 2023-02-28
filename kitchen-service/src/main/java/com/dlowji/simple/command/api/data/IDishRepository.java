package com.dlowji.simple.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDishRepository extends JpaRepository<Dish, String> {
    List<Dish> findAllByCategory(Category category);
}
