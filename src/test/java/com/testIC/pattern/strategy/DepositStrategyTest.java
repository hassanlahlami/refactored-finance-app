package com.testIC.pattern.strategy;

import com.testIC.pattern.factory.AccountFactory;
import com.testIC.service.BankingService;
import com.testIC.service.TransactionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DepositStrategyTest {

    @Test
    public void shouldDepositMoneyCorrectly() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 1000));

        TransactionStrategy strategy =
                new DepositStrategy("user1", 200);

        strategy.execute(bs);

        assertEquals(1200, bs.getBalance("user1"));
    }
    @Test
    void depositOfZeroShouldNotChangeBalance() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 500));

        TransactionStrategy strategy =
                new DepositStrategy("user1", 0);

        strategy.execute(bs);

        assertEquals(500, bs.getBalance("user1"));
    }

    @Test
    void depositNegativeAmountShouldNotChangeBalance() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 500));

        TransactionStrategy strategy =
                new DepositStrategy("user1", -100);

        strategy.execute(bs);

        assertEquals(500, bs.getBalance("user1"));
    }
}

