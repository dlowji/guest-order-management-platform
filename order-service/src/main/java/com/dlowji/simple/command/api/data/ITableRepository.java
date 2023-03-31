package com.dlowji.simple.command.api.data;

import com.dlowji.simple.command.api.enums.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITableRepository extends JpaRepository<SeveredTable, String> {
    List<SeveredTable> findAllByCapacity(int capacity);

    List<SeveredTable> findAllByTableStatus(TableStatus tableStatus);

    Optional<SeveredTable> findByCode(String code);
}
