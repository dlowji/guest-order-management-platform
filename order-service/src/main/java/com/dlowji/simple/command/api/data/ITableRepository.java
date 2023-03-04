package com.dlowji.simple.command.api.data;

import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.model.TableResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITableRepository extends JpaRepository<SeveredTable, String> {
    List<SeveredTable> findAllByCapacity(int capacity);

    List<SeveredTable> findAllByTableStatus(TableStatus tableStatus);
}
