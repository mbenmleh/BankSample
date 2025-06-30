package tn.eternity.bank.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.eternity.bank.domain.Account;
import tn.eternity.bank.domain.Transaction;
import tn.eternity.bank.repository.AccountRepository;
import tn.eternity.bank.service.AccountService;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
  private AccountRepository repository;

  @Override
  public Account createAccount(String owner) {
    Account newAccount = new Account();
    newAccount.setOwner(owner);
    return repository.save(newAccount);
  }

  @Override
  public Account depositAccount(UUID id, Double amount) {
    // Type Inference
    Optional<Account> existingAccount = repository.findById(id);
    if (existingAccount.isEmpty()) {
      throw new IllegalStateException("Account not found");
    }
    if (amount < 0) {
      throw new IllegalStateException("Invalid amount");
    }

    Account newState = existingAccount.get();
    newState.setBalance(existingAccount.get().getBalance() + amount);

    // Create a new transaction record
    Transaction transaction =
        Transaction.builder()
            .type("DEPOSIT")
            .timestamp(LocalDateTime.now())
            .amount(amount)
            .description("Deposit of " + amount + " to account " + id)
            .account(existingAccount.get())
            .build();
    newState.getTransactions().add(transaction);
    return repository.save(newState);
  }

  @Override
  public void transferAccount(UUID from, UUID to, Double amount) {
    Account fromAccount = checkIfExists(from);
    Account toAccount = checkIfExists(to);
    if (amount < 0) {
      throw new IllegalStateException("Invalid amount");
    }
    if (amount > fromAccount.getBalance()) {
      throw new IllegalStateException("Insufficient balance");
    }
    fromAccount.setBalance(fromAccount.getBalance() - amount);
    toAccount.setBalance(toAccount.getBalance() + amount);
    // Create a new transaction record for the sender
    Transaction senderTransaction =
        Transaction.builder()
            .type("TRANSFER")
            .timestamp(LocalDateTime.now())
            .amount(-amount)
            .description("Transfer of " + amount + " to account " + to)
            .account(fromAccount)
            .build();
    fromAccount.getTransactions().add(senderTransaction);
    // Create a new transaction record for the receiver
    Transaction receiverTransaction =
        Transaction.builder()
            .type("TRANSFER")
            .timestamp(LocalDateTime.now())
            .amount(amount)
            .description("Received " + amount + " from account " + from)
            .account(toAccount)
            .build();
    toAccount.getTransactions().add(receiverTransaction);
    repository.save(fromAccount);
    repository.save(toAccount);
  }

  private Account checkIfExists(UUID id) {
    Optional<Account> existingAccount = repository.findById(id);
    if (existingAccount.isEmpty()) {
      throw new IllegalStateException("Account not found");
    }
    return existingAccount.get();
  }

  @Override
  public Account withdrawAccount(UUID id, Double amount) {
    Account existingAccount = checkIfExists(id);
    if (amount > existingAccount.getBalance()) {
      throw new IllegalStateException("Insuffiant Balance");
    }

    existingAccount.setBalance(existingAccount.getBalance() - amount);

    // Create a new transaction record
    Transaction transaction =
        Transaction.builder()
            .type("WITHDRAWAL")
            .timestamp(LocalDateTime.now())
            .amount(-amount)
            .description("Withdrawal of " + amount + " from account " + id)
            .account(existingAccount)
            .build();
    existingAccount.getTransactions().add(transaction);
    return repository.save(existingAccount);
  }

  @Override
  public List<Account> getAllAccount() {
    return repository.findAll();
  }
}
