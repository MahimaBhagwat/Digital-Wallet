package com.model;

public class Wallet {
	private String walletId;
	private User user;
	private double balance;
	
	public Wallet(String walletId, User user) {
		if (walletId == null || walletId.trim().isEmpty()) {
            throw new IllegalArgumentException("Wallet ID cannot be null or empty");
        }
		if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        this.walletId = walletId;
        this.user = user;
        this.balance = 0.0;
	}
	
	public String getWalletId() {
        return walletId;
    }

    public User getUser() {
        return user;
    }

    public double getBalance() {
        return balance;
    }
    
    public void addMoney(double amount) {
    	
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (amount > 50000) {
            System.out.println("Warning: Large amount added!");
        }

        balance += amount;
    }
    
    public void deductMoney(double amount) {

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        balance -= amount;
    }
    
    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }
    
    @Override
    public String toString() {
        return "Wallet{" +
                "walletId='" + walletId + '\'' +
                ", userId='" + user.getUserId() + '\'' +
                ", balance=" + balance +
                '}';
    }
	
    
	
}
