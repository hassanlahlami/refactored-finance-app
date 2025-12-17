package com.testIC.pattern.factory;


import com.testIC.model.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountFactoryTest {

    @Test
    void shouldCreateAccountWithInitialBalance() {
        Account acc = AccountFactory.create("user1", 1000);

        assertEquals("user1", acc.getOwner());
        assertEquals(1000, acc.getBalance());
    }


    @Test
    void initialBalanceCanBeZero() {
        Account acc = AccountFactory.create("userZero", 0);
        assertEquals(0, acc.getBalance());
    }

    @Test
    void negativeInitialBalanceIsAllowed() {
        Account acc = AccountFactory.create("userNeg", -100);
        assertEquals(-100, acc.getBalance());
    }
}
