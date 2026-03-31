package com.junit;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
class BankAccountTest {
	BankAccount account;

    @BeforeEach
    void setUp() {
        account=new BankAccount(100.0);
        System.out.println("Test setup complete...");
    }

    @Test
    void testDepositIncreasesBalance() {
        account.deposit(50.0);
        double balance = account.getBalance();
        System.out.println("After deposit, balance is- "+balance);
        assertEquals(150.0, balance, "Balance should increase after deposit.");
    }

    @Test
    void testWithdrawDecreasesBalance() {
        boolean success=account.withdraw(40.0);
        double balance =account.getBalance();
        System.out.println("After withdrawal, balance- "+balance);
        assertTrue(success, "Withdrawal should succeed.");
        assertEquals(60.0, balance, "Balance should decrease after withdrawal.");
    }

    @Test
    void testWithdrawFailsIfInsufficientFunds() {
        boolean success=account.withdraw(200.0);
        double balance =account.getBalance();
        System.out.println("Attempted over-withdrawal, balance is- "+balance);
        assertFalse(success, "Withdrawal should fail if funds are insufficient");
        assertEquals(100.0, balance, "Balance should remain unchanged after failed withdrawal.");
    }
}