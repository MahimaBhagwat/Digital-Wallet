package com.main;

import com.model.User;
import com.service.WalletService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        WalletService service = new WalletService();

        while (true) {

            System.out.println("\n===== DIGITAL WALLET SYSTEM =====");
            System.out.println("1. Create Wallet");
            System.out.println("2. Add Money");
            System.out.println("3. Send Money");
            System.out.println("4. View Transactions");
            System.out.println("5. Show Large Transactions");
            System.out.println("6. Sort Transactions by Amount");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            try {

                switch (choice) {

                    case 1:
                        System.out.print("Enter User ID: ");
                        String userId = sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Phone Number: ");
                        String phone = sc.nextLine();

                        System.out.print("Enter Wallet ID: ");
                        String walletId = sc.nextLine();

                        User user = new User(userId, name, phone);
                        service.createWallet(user, walletId);
                        break;

                    case 2:
                        System.out.print("Enter Wallet ID: ");
                        String wId1 = sc.nextLine();

                        System.out.print("Enter Amount: ");
                        double amount1;
                        try {
                            amount1 = Double.parseDouble(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid amount!");
                            break;
                        }

                        service.addMoney(wId1, amount1);
                        break;

                    case 3:
                        System.out.print("Enter Sender Wallet ID: ");
                        String fromId = sc.nextLine();

                        System.out.print("Enter Receiver Wallet ID: ");
                        String toId = sc.nextLine();

                        System.out.print("Enter Amount: ");
                        double amount2;
                        try {
                            amount2 = Double.parseDouble(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid amount!");
                            break;
                        }

                        service.sendMoney(fromId, toId, amount2);
                        break;

                    case 4:
                        System.out.print("Enter Wallet ID: ");
                        String wId2 = sc.nextLine();

                        service.viewTransactions(wId2);
                        break;

                    case 5:
                        System.out.print("Enter Wallet ID: ");
                        String wId3 = sc.nextLine();

                        System.out.print("Enter Limit Amount: ");
                        double limit;
                        try {
                            limit = Double.parseDouble(sc.nextLine());
                        } catch (Exception e) {
                            System.out.println("Invalid amount!");
                            break;
                        }

                        service.showLargeTransactions(wId3, limit);
                        break;

                    case 6:
                        System.out.print("Enter Wallet ID: ");
                        String wId4 = sc.nextLine();

                        service.sortTransactionsByAmount(wId4);
                        break;

                    case 7:
                        System.out.println("Exiting... Thank you!");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}