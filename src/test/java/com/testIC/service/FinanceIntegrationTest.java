package com.testIC.service;


import com.testIC.pattern.factory.AccountFactory;
import com.testIC.pattern.observer.AuditLogger;
import com.testIC.pattern.observer.NotificationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FinanceIntegrationTest {

    @Test
    void fullBankingScenarioShouldWorkCorrectly() {

        TransactionService ts = new TransactionService();
        ts.addObserver(new AuditLogger());
        ts.addObserver(new NotificationService());

        BankingService bs = new BankingService(ts);

        bs.addAccount(AccountFactory.create("user1", 1000));
        bs.addAccount(AccountFactory.create("user2", 500));

        bs.deposit("user1", 300);
        bs.transfer("user1", "user2", 200);

        assertEquals(1100, bs.getBalance("user1"));
        assertEquals(700, bs.getBalance("user2"));
    }
}