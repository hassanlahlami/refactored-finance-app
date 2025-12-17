package com.testIC.pattern.strategy;

import com.testIC.pattern.factory.AccountFactory;
import com.testIC.service.BankingService;
import com.testIC.service.TransactionService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawStrategyTest {
    @Test
    public void shouldWithdrawMoneyIfBalanceIsEnough() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 1000));

        TransactionStrategy strategy =
                new WithdrawStrategy("user1", 300);
        strategy.execute(bs);
        assertEquals(700, bs.getBalance("user1"));
    }
    @Test
    void shouldNotWithdrawIfBalanceIsInsufficient() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 100));

        TransactionStrategy strategy =
                new WithdrawStrategy("user1", 500);

        strategy.execute(bs);

        assertEquals(100, bs.getBalance("user1"));
    }
    @Test
    void withdrawZeroShouldNotChangeBalance() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 400));

        TransactionStrategy strategy =
                new WithdrawStrategy("user1", 0);

        strategy.execute(bs);

        assertEquals(400, bs.getBalance("user1"));
    }

    @Test
    void withdrawNegativeAmountShouldNotChangeBalance() {
        TransactionService ts = new TransactionService();
        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 400));

        TransactionStrategy strategy =
                new WithdrawStrategy("user1", -50);

        strategy.execute(bs);

        assertEquals(400, bs.getBalance("user1"));
    }
}