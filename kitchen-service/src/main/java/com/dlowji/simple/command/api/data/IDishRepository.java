package com.dlowji.simple.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDishRepository extends JpaRepository<Dish, String> {
    List<Dish> findAllByCategory(Category category);

    Optional<Dish> findByTitle(String title);
}
