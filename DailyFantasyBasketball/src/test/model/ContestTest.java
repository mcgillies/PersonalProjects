package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContestTest {

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
    private Player drew;
    private Lineup lineup1;
    private Lineup lineup2;
    private Lineup lineup3;
    private Contest someContest;
    private Account myAccount;
    private Account notMyAccount;
    private Account otherAccount;


    @BeforeEach
    void runBefore() {
        dejounte = new Player("Dejounte Murray", "SAS", "PG", 80);
        lebron = new Player("Lebron James", "LAL", "SF", 110);
        kevin = new Player("Kevin Durant", "BKN", "SF", 100);
        steph = new Player("Steph Curry", "GSW", "PG", 90);
        domantas = new Player("Domantas Sabonis", "IND", "PF", 80);
        keldon = new Player("Keldon Johnson", "SAS", "SF", 70);
        bones = new Player("Bones Hyland", "DEN", "PG", 55);
        felix = new Player("Felix McNally", "WSH", "C", 30);
        brendan = new Player("Brendan Kelly", "LAL", "PG", 20);
        caris = new Player("Caris Levert", "IND", "SG", 70);
        michael = new Player("Michael Finley", "DAL", "SF", 60);
        giannis = new Player("Giannis Antetokounmpo", "MIL", "PF", 120);
        karl = new Player("Karl-Anthony Towns", "MIN", "C", 40);
        drew = new Player("Drew Eubanks", "SAS", "C", 20);
        myAccount = new Account(10, "mgillies");
        notMyAccount = new Account(9, "brokeboi");
        otherAccount = new Account(30, "fantasydad11");
        lineup1 = new Lineup(myAccount);
        lineup1.addPlayer(dejounte);
        lineup1.addPlayer(domantas);
        lineup1.addPlayer(steph);
        lineup1.addPlayer(keldon);
        lineup1.addPlayer(bones);
        lineup1.addPlayer(felix);
        lineup1.addPlayer(caris);
        lineup1.addPlayer(michael);
        lineup1.addPlayer(karl);
        lineup1.addPlayer(brendan);
        lineup2 = new Lineup(notMyAccount);
        lineup2.addPlayer(dejounte);
        lineup2.addPlayer(domantas);
        lineup2.addPlayer(steph);
        lineup2.addPlayer(keldon);
        lineup2.addPlayer(bones);
        lineup2.addPlayer(felix);
        lineup2.addPlayer(caris);
        lineup2.addPlayer(michael);
        lineup2.addPlayer(karl);
        lineup2.addPlayer(drew);
        lineup3 = new Lineup(otherAccount);
        lineup3.addPlayer(dejounte);
        lineup3.addPlayer(domantas);
        lineup3.addPlayer(steph);
        lineup3.addPlayer(keldon);
        lineup3.addPlayer(bones);
        lineup3.addPlayer(felix);
        lineup3.addPlayer(caris);
        lineup3.addPlayer(michael);
        lineup3.addPlayer(karl);
        lineup3.addPlayer(drew);
        someContest = new Contest(2, 10, 20);
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
        drew.addStats(4, 0, 5, 0, 1,
                0, false);
        brendan.addStats(0, 0, 0, 0, 0,
                0, false);

    }

    @Test
    public void testNumberEntriesNone() {

        assertEquals(0, someContest.numberEntries());
        assertEquals(2, someContest.getNumEntries());
        assertEquals(10, someContest.getFee());
        assertEquals(20, someContest.getPrize());
    }

    @Test
    public void testNumberEntriesSome() {
        someContest.addLineup(lineup1);
        assertEquals(1, someContest.numberEntries());
    }

    @Test
    public void testAddLineupHasFunds() {
        someContest.addLineup(lineup1);
        assertEquals(1, someContest.numberEntries());
        assertEquals(0, myAccount.getBalance());
    }

    @Test
    public void testAddLineupNoFunds() {
        someContest.addLineup(lineup2);
        assertEquals(0, someContest.numberEntries());
        assertEquals(9, notMyAccount.getBalance());
    }

    @Test
    public void testAddLineupTooMany() {
        someContest.addLineup(lineup1);
        someContest.addLineup(lineup2);
        someContest.addLineup(lineup3);
        assertEquals(2, someContest.numberEntries());
    }

    @Test
    public void testAddLineupTooManyNoFunds() {
        someContest.addLineup(lineup1);
        someContest.addLineup(lineup3);
        someContest.addLineup(lineup2);
        assertEquals(2, someContest.numberEntries());
    }

    @Test
    public void testShowWinningScore() {
        someContest.addLineup(lineup1);
        assertEquals(307, someContest.showWinningScore());
    }

    @Test
    public void testShowWinningScoreTwoEntries() {
        someContest.addLineup(lineup1);
        someContest.addLineup(lineup3);
        assertEquals(324, someContest.showWinningScore());
    }

    @Test
    public void testFindWinningAccountOne() {
        someContest.addLineup(lineup1);
        assertEquals(myAccount, someContest.findWinningAccount());
    }

    @Test
    public void testFindWinningAccountMore() {
        someContest.addLineup(lineup1);
        someContest.addLineup(lineup3);
        assertEquals(otherAccount, someContest.findWinningAccount());
    }

    @Test
    public void testCreditPrize() {
        someContest.addLineup(lineup1);
        someContest.addLineup(lineup3);
        someContest.creditPrize();
        assertEquals(40, otherAccount.getBalance());
    }


}
