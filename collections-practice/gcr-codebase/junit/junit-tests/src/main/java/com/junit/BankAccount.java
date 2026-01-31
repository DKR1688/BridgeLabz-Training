package com.junit;

public class BankAccount {
    double balance;

    BankAccount(double initialBalance) {
        this.balance=initialBalance;
        System.out.println("Account created with balance- "+initialBalance);
    }

    public void deposit(double amount) {
        if (amount>0) {
            balance +=amount;
            System.out.println("Deposited- "+amount+ ", New balance- "+balance);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount>0 && amount<=balance) {
            balance -=amount;
            System.out.println("Withdrawn- "+amount + ", New balance- "+balance);
            return true;
        } else {
            System.out.println("Withdrawal failed! Insufficient funds or invalid amount.");
            return false;
        }
    }

    public double getBalance() {
        System.out.println("Current balance- "+balance);
        return balance;
    }
}
