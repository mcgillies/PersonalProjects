package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents an NBA player
public class Player implements Writable {
    private String name;   // Player's name
    private String team;   // Player's team
    private String position; // Player's position
    private int salary;    // Player's salary
    private int points;    // Points scored by player
    private int threes;    // Number of 3-pointers made by player
    private int rebounds;  // Number of rebounds by player
    private int assists;   // Number of assists by player
    private int blocks;    // Number of blocks by player
    private int steals;    // Number of steals by player
    private boolean ejected;  // True if the player has been ejected
    private int fpoints;      // Total number of fantasy points accumulated by player

    // REQUIRES: Player name, position and team have length > 0, salary is > 0
    // EFFECTS: Player name, position and team (3 letter abbreviation) are set, salary is assigned
    public Player(String playerName, String playerTeam, String playerPosition, int playerSalary) {
        name = playerName;
        team = playerTeam;
        position = playerPosition;
        salary = playerSalary;
        fpoints = 0;
        points = 0;
        threes = 0;
        rebounds = 0;
        assists = 0;
        blocks = 0;
        steals = 0;
        ejected = false;
    }

    // EFFECTS: returns player name
    public String getName() {
        return name;
    }

    // EFFECTS: returns player team
    public String getTeam() {
        return team;
    }

    // EFFECTS: returns player position
    public String getPosition() {
        return position;
    }

    // EFFECTS: returns player salary
    public int getSalary() {
        return salary;
    }

    // EFFECTS: returns player fantasy points
    public int getFpoints() {
        return fpoints;
    }

    // MODIFIES: this
    // EFFECTS: Calculates the total number of fantasy points a player has earned
    public int calculateFantasyPoints() {
        fpoints = points + threes + (2 * assists) + (2 * rebounds) + (3 * blocks) + (3 * steals);
        if (ejected) {
            return fpoints += 20;
        } else {
            return fpoints;
        }
    }

    //REQUIRES: all stats inputted must be > 0
    // MODIFIES: this
    // EFFECTS: Assigns the players stats to the player
    public void addStats(int numPoints, int numThrees, int numRebounds, int numAssists, int numBlocks, int
            numSteals, boolean isEjected) {
        points = numPoints;
        threes = numThrees;
        rebounds = numRebounds;
        assists = numAssists;
        blocks = numBlocks;
        steals = numSteals;
        ejected = isEjected;
    }

    public int getPoints() {
        return points;
    }

    public int getThrees() {
        return threes;
    }

    public int getRebounds() {
        return rebounds;
    }

    public int getAssists() {
        return assists;
    }

    public int getBlocks() {
        return blocks;
    }

    public int getSteals() {
        return steals;
    }

    public boolean getEjected() {
        return ejected;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("team", team);
        json.put("position", position);
        json.put("salary", salary);
        return json;
    }


}
