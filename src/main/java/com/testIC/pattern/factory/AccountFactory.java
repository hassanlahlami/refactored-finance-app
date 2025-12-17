package com.testIC.pattern.factory;

import com.testIC.model.Account;

public class AccountFactory {
    public static Account create(String owner, double initialBalance) {
        return new Account(owner, initialBalance);
    }
}
