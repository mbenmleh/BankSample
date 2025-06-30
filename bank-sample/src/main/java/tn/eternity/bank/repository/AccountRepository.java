package tn.eternity.bank.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.eternity.bank.domain.Account;

public interface AccountRepository  extends JpaRepository<Account,UUID>{
    
}
