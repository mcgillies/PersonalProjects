package model;


// Represents a user's account
public class Account {
    private int balance;
    private String username;

    // Requires initialBalance >= 0, userName has length > 0
    // EFFECTS: constructs an account with given initial balance in dollars and username
    public Account(int initialBalance, String userName) {
        balance = initialBalance;
        username = userName;
    }

    //REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Adds amount (in dollars) to balance
    public void addFunds(int amount) {
        balance += amount;
    }

    //REQUIRES: amount >= 0 and amount <= balance
    // MODIFIES: this
    // EFFECTS: Removes amount (in dollars) to balance
    public void removeFunds(int amount) {
        balance -= amount;
    }

    // EFFECTS: returns current balance
    public int getBalance() {
        return balance;
    }

    // EFFECTS: returns username
    public String getUsername() {
        return username;
    }


}
