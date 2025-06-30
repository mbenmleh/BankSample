package tn.eternity.bank.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import tn.eternity.bank.domain.Account;

@Service
public interface AccountService {
    Account createAccount(String owner);

    Account depositAccount(UUID id, Double amount);

    void transferAccount(UUID from, UUID to,Double amount);

    Account withdrawAccount(UUID id, Double amount);


    List<Account> getAllAccount();
}
