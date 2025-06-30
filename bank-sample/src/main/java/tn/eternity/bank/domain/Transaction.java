package tn.eternity.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String type; // "DEPOSIT", "WITHDRAWAL", "TRANSFER"
  private Double amount;
  private LocalDateTime timestamp;
  private String description;

  @ManyToOne
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;
}
