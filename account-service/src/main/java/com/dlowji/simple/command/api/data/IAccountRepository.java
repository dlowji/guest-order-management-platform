package com.dlowji.simple.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account, String> {
    boolean existsByUsername(String username);
}
