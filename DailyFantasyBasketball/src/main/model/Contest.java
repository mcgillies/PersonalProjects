package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

//Represents a contest that lineups can be entered in
public class Contest {

    private List<Lineup> thisContest;
    private int numEntries;
    private int fee;
    private int prize;

    //REQUIRES: Entry fee is > 0, Prize equals number of lineups allowed * fee
    //EFFECTS: Creates a contest with a list of lineups, number of possible entries, entry fee and prize
    public Contest(int totalEntries, int entryFee, int prizeMoney) {
        thisContest = new ArrayList<>();
        numEntries = totalEntries;
        fee = entryFee;
        prize = prizeMoney;
    }

    //REQUIRES: Number of entries is less than the limit of entries
    //EFFECTS: Calculates the number of entries in the contest
    public int numberEntries() {
        return thisContest.size();
    }

    //REQUIRES: Lineup must be full
    //MODIFIES: this, myAccount
    //EFFECTS: Enters a users lineup into a contest if the contest
    // has space, the user has the required funds, and removes the entry fee from account
    public void addLineup(Lineup myLineup) {
        Account lineupAccount = myLineup.getAccount();
        if (thisContest.size() < numEntries && lineupAccount.getBalance() >= fee) {
            thisContest.add(myLineup);
            lineupAccount.removeFunds(fee);
        }
        EventLog.getInstance().logEvent(new Event("Lineup has been added to a contest."));
    }

    // REQUIRES: Contest has at least one entry
    //MODIFIES: this
    //EFFECTS: Returns the largest fantasy score of all entries
    public Integer showWinningScore() {
        List<Integer> listFantasyPoints = new ArrayList<>();
        for (Lineup lineup : thisContest) {
            listFantasyPoints.add(lineup.getFantasyScoreLineup());

        }

        return Collections.max(listFantasyPoints);
    }

    //EFFECTS: Returns the account that is responsible for the lineup with the highest fantasy score
    public Account findWinningAccount() {
        Account winningAccount = new Account(0, "no winner");
        for (Lineup lineup : thisContest) {
            if (lineup.getFantasyScoreLineup() == showWinningScore()) {
                winningAccount = lineup.getAccount();
            }
        }
        return winningAccount;

    }

    //MODIFIES: Account
    //EFFECTS: Credits user with the winning lineup with the prize money
    public void creditPrize() {
        findWinningAccount().addFunds(prize);

    }

    //EFFECTS: Returns the total number of entries permitted in a contest
    public int getNumEntries() {
        return numEntries;
    }

    //EFFECTS: Returns the entry fee
    public int getFee() {
        return fee;
    }

    //EFFECTS: returns the prize pool
    public int getPrize() {
        return prize;
    }


}
