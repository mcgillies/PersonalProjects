package model;

import java.util.ArrayList;
import java.util.List;

import static model.Lineup.*;
import static model.Player.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LineupTest {
    private Player dejounte;
    private Player lebron;
    private Player kevin;
    private Player steph;
    private Player domantas;
    private Player keldon;
    private Player bones;
    private Player felix;
    private Player brendan;
    private Player caris;
    private Player michael;
    private Player giannis;
    private Player karl;
    private Lineup empty;
    private Lineup almostFull;
    private Account myAccount;

    @BeforeEach
    void runBefore() {
        dejounte = new Player("Dejounte Murray", "SAS", "PG", 80);
        lebron = new Player("Lebron James", "LAL", "SF", 110);
        kevin = new Player("Kevin Durant", "BKN", "SF", 25);
        steph = new Player("Steph Curry", "GSW", "PG", 90);
        domantas = new Player("Domantas Sabonis", "IND", "PF", 80);
        keldon = new Player("Keldon Johnson", "SAS", "SF", 70);
        bones = new Player("Bones Hyland", "DEN", "PG", 55);
        felix = new Player("Felix McNally", "WSH", "C", 30);
        brendan = new Player("Brendan Kelly", "LAL", "PG", 20);
        caris = new Player("Caris Levert", "IND", "SG", 70);
        michael = new Player("Michael Finley", "DAL", "SF", 60);
        giannis = new Player("Giannis Antetokounmpo", "MIL", "PF", 5);
        karl = new Player("Karl-Anthony Towns", "MIN", "C", 40);
        myAccount = new Account(2, "mgillies");
        empty = new Lineup(myAccount);
        almostFull = new Lineup(myAccount);
        almostFull.addPlayer(dejounte);
        almostFull.addPlayer(domantas);
        almostFull.addPlayer(steph);
        almostFull.addPlayer(keldon);
        almostFull.addPlayer(bones);
        almostFull.addPlayer(felix);
        almostFull.addPlayer(caris);
        almostFull.addPlayer(michael);
        almostFull.addPlayer(karl);
        dejounte.addStats(20, 2, 6, 6,
                1, 1, false);
        domantas.addStats(16, 1, 10,
                3, 0, 0, false);
        steph.addStats(30, 6, 4, 4, 1,
                1, false);
        keldon.addStats(9, 0, 4, 2,
                2, 0, false);
        bones.addStats(6, 1, 0, 2, 0,
                1, false);
        felix.addStats(3, 0, 0, 0,
                1, 0, false);
        caris.addStats(15, 2, 2, 2, 1,
                0, false);
        michael.addStats(8, 2, 5, 1, 0,
                0, false);
        karl.addStats(20, 3, 11, 3, 2,
                0, false);


    }

    @Test
    void testAddPlayerBasic() {
        empty.addPlayer(karl);
        assertEquals(1, empty.numPlayers());
    }

    @Test
    void testAddPlayerToFull() {
        almostFull.addPlayer(brendan);
        assertEquals(10, almostFull.numPlayers());
    }

    @Test
    void testAddPlayerExactSalary() {
        almostFull.addPlayer(kevin);
        assertEquals(10, almostFull.numPlayers());
        assertEquals(600, almostFull.calculateTotalSalary());

    }

    @Test
    void testAddPlayerTooMuch() {
        almostFull.addPlayer(lebron);
        assertEquals(9, almostFull.numPlayers());
    }

    @Test
    void testAddPlayerTooMany() {
        almostFull.addPlayer(brendan);
        almostFull.addPlayer(giannis);
        assertEquals(10, almostFull.numPlayers());
    }

    @Test
    void testRemovePlayer() {
        empty.addPlayer(dejounte);
        empty.addPlayer(felix);
        empty.removePlayer(dejounte);
        assertEquals(1, empty.numPlayers());
    }


    @Test
    void testCalculateTotalSalaryEmpty() {
        assertEquals(0, empty.calculateTotalSalary());
    }

    @Test
    void testCalculateTotalSalarySome() {
        empty.addPlayer(dejounte);
        empty.addPlayer(lebron);
        empty.addPlayer(brendan);
        assertEquals(210, empty.calculateTotalSalary());
    }

    @Test
    void testGetFantasyScoreLineup() {
        assertEquals(307, almostFull.getFantasyScoreLineup());
    }

    @Test
    void testGetAccount() {
        assertEquals(myAccount, almostFull.getAccount());
    }

    @Test
    void testIsFullNot() {
        assertFalse(almostFull.isFull());
    }

    @Test
    void testIsFullYes() {
        almostFull.addPlayer(brendan);
        assertTrue(almostFull.isFull());
    }

    @Test
    void testGetPlayer() {
        assertEquals(dejounte, almostFull.getPlayer(0));
    }


}

