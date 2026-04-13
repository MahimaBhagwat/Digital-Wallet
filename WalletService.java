package com.service;

import com.model.*;
import java.util.*;
import com.model.TransactionType;

public class WalletService {
	
	private Map<String, Wallet> walletMap = new HashMap<>();
    private Map<String, List<Transaction>> transactionMap = new HashMap<>();
    private Set<User> users = new HashSet<>();
    
    public void createWallet(User user, String walletId) {
    	if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    	if (walletId == null || walletId.trim().isEmpty()) {
    	    throw new IllegalArgumentException("Wallet ID cannot be null or empty");
    	}
    	if (walletMap.containsKey(walletId)) {
            throw new IllegalArgumentException("Wallet ID already exists");
        }

        if (users.contains(user)) {
            System.out.println("User already exists, creating another wallet...");
        } else {
            users.add(user);
        }
        
        Wallet wallet = new Wallet(walletId, user);
        walletMap.put(walletId, wallet);
        transactionMap.put(walletId, new ArrayList<>());

        System.out.println("Wallet created successfully!");
    }
    
    public void addMoney(String walletId, double amount) {
    	
    	if (amount <= 0) {
    	    throw new IllegalArgumentException("Invalid amount");
    	}
    	
    	Wallet wallet = walletMap.get(walletId);

        if (wallet == null) {
            throw new IllegalArgumentException("Wallet not found");
        }

        wallet.addMoney(amount);
        
        Transaction t = new Transaction(
                generateTransactionId(),
                null,
                walletId,
                amount,
                TransactionType.CREDIT
        );
        
        transactionMap.computeIfAbsent(walletId, k -> new ArrayList<>()).add(t);

        System.out.println("Money added successfully!");
    }
    
    public void sendMoney(String fromId, String toId, double amount) {
    	
    	if (amount <= 0) {
    	    throw new IllegalArgumentException("Invalid amount");
    	}
    	
    	if (fromId == null || toId == null) {
    	    throw new IllegalArgumentException("Wallet IDs cannot be null");
    	}

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Cannot transfer to same wallet");
        }

        Wallet sender = walletMap.get(fromId);
        Wallet receiver = walletMap.get(toId);

        if (sender == null || receiver == null) {
            throw new IllegalArgumentException("Invalid wallet ID");
        }

        if (!sender.hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        sender.deductMoney(amount);
        receiver.addMoney(amount);

        String txId = generateTransactionId();

        Transaction debit = new Transaction(txId, fromId, toId, amount, TransactionType.DEBIT);
        Transaction credit = new Transaction(txId, fromId, toId, amount, TransactionType.CREDIT);

        transactionMap.computeIfAbsent(fromId, k -> new ArrayList<>()).add(debit);
        transactionMap.computeIfAbsent(toId, k -> new ArrayList<>()).add(credit);

        System.out.println("Transaction successful!");
    }
    
    public void viewTransactions(String walletId) {

        List<Transaction> list = transactionMap.get(walletId);

        if (list == null || list.isEmpty()) {
            System.out.println("No transactions found");
            return;
        }

        list.forEach(System.out::println);
    }
    
    public void showLargeTransactions(String walletId, double limit) {

        List<Transaction> list = transactionMap.get(walletId);

        if (list == null || list.isEmpty()) {
            System.out.println("No transactions found");
            return;
        }

        boolean found = false;

        for (Transaction t : list) {
            if (t.getAmount() > limit) {
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions above given limit");
        }
    }
    
    public void sortTransactionsByAmount(String walletId) {

        List<Transaction> list = transactionMap.get(walletId);
        
        if (list == null || list.isEmpty()) {
            System.out.println("No transactions to sort");
            return;
        }

        list.sort(new TransactionComparator());
        list.forEach(System.out::println);

//        if (list == null) return;
//
//        //list.sort((t1, t2) -> Double.compare(t2.getAmount(), t1.getAmount()));
//        list.sort(new TransactionComparator());
//
//        list.forEach(System.out::println);
        
    }
    
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
    
    

}
