package tn.eternity.bank.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;


public class AccountTest {

    @Test
    void test_EmptyAccount() {
        Account a1 = new Account();
        assertEquals(0, a1.getBalance());
        assertEquals(null, a1.getOwner());
    }

    @Test
    void test_FullAccount(){
        UUID unique = UUID.randomUUID();
        LocalDateTime date = LocalDateTime.now();

        Account fullAccount = new Account(unique, "Elyes", 1000D, date);
        assertEquals(unique, fullAccount.getId());
        assertTrue("Elyes".equals(fullAccount.getOwner()));
        assertNotEquals(999D, fullAccount.getBalance());
        assertTrue(fullAccount.getCreatedAt().equals(date));
        
    }
}