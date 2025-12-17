package com.testIC.service;

import com.testIC.pattern.factory.AccountFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankingServiceTest {

    @Test
    void depositShouldIncreaseBalance() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 1000));
        bs.deposit("user1", 100);

        assertEquals(1100, bs.getBalance("user1"));
    }
    @Test
    void depositOnUnknownAccountShouldThrowException() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        assertThrows(RuntimeException.class,
                () -> bs.deposit("unknown", 100));
    }

    @Test
    void transferToSameAccountShouldNotChangeBalance() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 500));

        bs.transfer("user1", "user1", 200);

        assertEquals(500, bs.getBalance("user1"));
    }

    @Test
    void depositThenWithdrawShouldRestoreBalance() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 1000));

        bs.deposit("user1", 200);
        bs.withdraw("user1", 200);

        assertEquals(1000, bs.getBalance("user1"));
    }
    @Test
    void multipleDepositsShouldAccumulateCorrectly() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 100));

        bs.deposit("user1", 100);
        bs.deposit("user1", 200);
        bs.deposit("user1", 300);

        assertEquals(700, bs.getBalance("user1"));
    }

    @Test
    void multipleWithdrawalsShouldAccumulateCorrectly() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 1000));

        bs.withdraw("user1", 100);
        bs.withdraw("user1", 200);

        assertEquals(700, bs.getBalance("user1"));
    }
}