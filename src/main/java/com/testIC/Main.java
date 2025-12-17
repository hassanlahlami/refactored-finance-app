package com.testIC;


import com.testIC.pattern.factory.AccountFactory;
import com.testIC.pattern.observer.AuditLogger;
import com.testIC.pattern.observer.NotificationService;
import com.testIC.service.BankingService;
import com.testIC.service.TransactionService;

public class Main {
    public static void main(String[] args) {
        TransactionService transactionService = new TransactionService();
        transactionService.addObserver(new AuditLogger());
        transactionService.addObserver(new NotificationService());
        BankingService bankingService = new BankingService(transactionService);
        bankingService.addAccount(AccountFactory.create("user1", 1000));
        bankingService.addAccount(AccountFactory.create("user2", 500));
        bankingService.deposit("user1", 200);
        bankingService.transfer("user1", "user2", 150);
        System.out.println("Solde user1: " + bankingService.getBalance("user1"));
        System.out.println("Solde user2: " + bankingService.getBalance("user2"));
    }
}
