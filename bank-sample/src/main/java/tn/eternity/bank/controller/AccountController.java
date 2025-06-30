package tn.eternity.bank.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import tn.eternity.bank.service.AccountService;
import tn.eternity.bank.domain.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("create")
    public ResponseEntity<Account> createAccount(@RequestParam("owner") String owner) {
        Account createdAccount = accountService.createAccount(owner);
        return ResponseEntity.ok().body(createdAccount);
    }

    @PostMapping("deposit/{id}")
    public ResponseEntity<Account> deposit(@PathVariable("id") String id, @RequestParam("amount") Double amount) {
        Account updatedAccount = accountService.depositAccount(UUID.fromString(id), amount);
        return ResponseEntity.ok().body(updatedAccount);
    }

    @PostMapping("withdraw/{id}")
    public ResponseEntity<Account> withdraw(@PathVariable("id") String id, @RequestParam("amount") Double amount) {
        Account updatedAccount = accountService.withdrawAccount(UUID.fromString(id), amount);
        return ResponseEntity.ok().body(updatedAccount);
    }

    @PostMapping("transfer/{sender}/{receiver}")
    public ResponseEntity<String> transfer(@PathVariable("sender") String sender,
            @PathVariable("receiver") String receiver, @RequestParam("amount") Double amount) {
        accountService.transferAccount(UUID.fromString(sender), UUID.fromString(receiver), amount);
        return ResponseEntity.ok().body("transfer done succesufly!");
    }

    @GetMapping("all")
    public ResponseEntity<List<Account>> getAllAccount(){
        return ResponseEntity.ok()
        .body(accountService.getAllAccount());
    }

}
