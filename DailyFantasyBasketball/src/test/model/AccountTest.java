package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTest {
    private Account myAccount;

    @BeforeEach
    void runBefore() {
         myAccount = new Account(0, "mgillies");
    }

    @Test
    void testAddFundsZero() {
        myAccount.addFunds(0);
        assertEquals(0, myAccount.getBalance());
    }

    @Test
    void testAddFundsSome() {
        myAccount.addFunds(10);
        assertEquals(10, myAccount.getBalance());
        assertEquals("mgillies", myAccount.getUsername());
    }

    @Test
    void testRemoveFundsZero() {
        myAccount.removeFunds(0);
        assertEquals(0, myAccount.getBalance());
    }

    @Test
    void testRemoveFundsSome() {
        myAccount.addFunds(20);
        myAccount.removeFunds(10);
        assertEquals(10, myAccount.getBalance());
    }

    @Test
    void testRemoveFundsAll() {
        myAccount.addFunds(20);
        myAccount.removeFunds(20);
        assertEquals(0, myAccount.getBalance());
    }
}
