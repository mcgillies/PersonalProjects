package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//Represents a lineup of NBA players
public class Lineup implements Writable {
    private static int SALARY_CAP = 600;

    private List<Player> myLineup;
    private Account thisAccount;

    // Creates a lineup with the given players and the associated account
    public Lineup(Account myAccount) {

        myLineup = new ArrayList<>();
        thisAccount = myAccount;
    }

    //EFFECTS: Calculates the total salary of all players in the lineup
    public int calculateTotalSalary() {
        int total = 0;
        for (Player player : myLineup) {
            total += player.getSalary();
        }
        return total;
    }


    //MODIFIES: this
    //EFFECTS: Adds the given player to the lineup
    public void addPlayer(Player player) {
        if ((calculateTotalSalary() + player.getSalary() <= SALARY_CAP) && (myLineup.size() <= 9)) {
            myLineup.add(player);
        }
        EventLog.getInstance().logEvent(new Event(player.getName() + " added to Lineup"));
    }


    //MODIFIES: this
    //EFFECTS: Removes the selected player from the lineup if the player is in the lineup, else do nothing
    public void removePlayer(Player player) {
        myLineup.remove(player);

    }

    //EFFECTS: Returns the total fantasy score of the lineup
    public int getFantasyScoreLineup() {
        int totalScore = 0;
        for (Player player : myLineup) {
            totalScore += player.calculateFantasyPoints();
        }
        return totalScore;
    }

    //EFFECTS: Returns the number of players in a lineup
    public int numPlayers() {
        return myLineup.size();
    }

    //EFFECTS: Returns true if the lineup is full, false otherwise
    public boolean isFull() {
        if (myLineup.size() == 10) {
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: returns the players in the lineup
    public List<Player> getPlayers() {
        return myLineup;
    }


    //EFFECTS: returns the account associated with the lineup
    public Account getAccount() {
        return thisAccount;
    }

    //REQUIRES: 0 < number < 10
    //EFFECTS: returns the Player at the given index in the lineup
    public Player getPlayer(int number) {
        return myLineup.get(number);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Account name", thisAccount.getUsername());
        json.put("Account balance", thisAccount.getBalance());
        json.put("Players", playersToJson());
        return json;
    }


    // EFFECTS: returns players in this lineup as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : myLineup) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}

