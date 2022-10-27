package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.Account;
import model.Lineup;
import model.Player;
import model.Contest;
import persistence.JsonReader;
import persistence.JsonWriter;

// Code referenced from https://github.students.cs.ubc.ca/CPSC210/TellerApp
// and https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


//Daily fantasy basketball application
public class FantasyApp {
    private static final String JSON_STORE = "./data/FullLineup.json";
    private Lineup myLineup;
    private Account myAccount;
    private Contest cheap;
    private Contest expensive;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the fantasy application
    public FantasyApp() {
        runFantasyApp();
    }


    //MODIFIES: this
    //EFFECTS: processes user input
    private void runFantasyApp() {
        boolean keepRunning = true;
        String command = null;


        initalize();


        while (keepRunning) {
            showMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("leave")) {
                keepRunning = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("App closed");
    }

    //MODIFIES: this
    //EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("start")) {
            createMyLineup();
        } else if (command.equals("add")) {
            addLineupToContest();
        } else if (command.equals("stats")) {
            addStatisticsMyLineup();
        } else if (command.equals("points")) {
            getLineupFPoints();
        } else if (command.equals("save")) {
            saveLineup();
        } else if (command.equals("load")) {
            loadLineup();
        } else {
            System.out.println("Selection not valid");
        }
    }


    //MODIFIES: this
    //EFFECTS: initializes all possible player selections, accounts, lineups and contests
    private void initalize() {
        myAccount = new Account(150, "fantasyking");
        myLineup = new Lineup(myAccount);
        cheap = new Contest(2, 10, 20);
        expensive = new Contest(2, 100, 200);
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

    }

    //EFFECTS: shows initial menu
    private void showMenu() {
        System.out.println("Welcome to the daily fantasy basketball app!");
        System.out.println("Type leave to exit the application");
        System.out.println("Type start to begin creating a lineup");
        System.out.println("Type add to add lineup to a contest");
        System.out.println("Type stats to add statistics to your players");
        System.out.println("Type points to get the fantasy points your lineup has accumulated");
        System.out.println("Type save to save your created lineup");
        System.out.println("Type load to load your previous lineup");
    }

    //MODIFIES: this
    //EFFECTS: Allows the user to add players to their Lineup
    private void createMyLineup() {
        System.out.println("Type in player name, team, position and salary");
        while (myLineup.numPlayers() < 10) {
            String nothing = input.nextLine();
            String name = input.nextLine();
            String team = input.nextLine();
            String position = input.nextLine();
            int salary = input.nextInt();
            Player player = new Player(name, team, position, salary);
            myLineup.addPlayer(player);
            System.out.println(name + " has been added to the lineup");
        }
        System.out.println("Congrats! You have made a lineup");
    }

    //MODIFIES: this
    //EFFECTS: Allows the user to enter the lineups into a contest
    private void addLineupToContest() {
        System.out.println("Choose a contest to enter: type e for expensive and c for cheap");
        String command = null;
        command = input.next();
        command = command.toLowerCase();
        if (command.equals("e")) {
            expensive.addLineup(myLineup);
        } else if (command.equals("c")) {
            cheap.addLineup(myLineup);
        } else {
            System.out.println("Invalid Input");
        }
        System.out.println("Congrats! Your lineup has been added to the contest");

    }

    //MODIFIES: all Player instances within the lineup
    //EFFECTS: Adds statistics to the desired players
    private void addStatisticsMyLineup() {
        String nothing = input.nextLine();
        System.out.println("Type in statistics: Points, 3pts, Rebounds, Assists, Blocks, Steals, ejected?");
        for (int i = 0; i < 10; myLineup.getPlayer(i++)) {
            System.out.println("Stats for " + myLineup.getPlayer(i).getName());
            int points = addStat("Points");
            int threes = addStat("Threes");
            int rebounds = addStat("Rebounds");
            int assists = addStat("Assists");
            int blocks = addStat("Blocks");
            int steals = addStat("Steals");
            boolean ejected = addEjected("Ejected");
            myLineup.getPlayer(i).addStats(points, threes, rebounds, assists, blocks, steals, ejected);
            System.out.println("Stats have been added for " + myLineup.getPlayer(i).getName());
        }
        System.out.println("Congrats! Stats have been added for all players in your lineup");

    }

    //MODFIES: Player
    //EFFECTS: allows the user to add the specified stat to each player
    private int addStat(String stat) {
        System.out.println("Enter the number of " + stat);
        String number = input.nextLine();
        return Integer.parseInt(number);

    }

    //MODIFIES: Player
    //EFFECTS: allows the user to input if the player has been ejected
    private boolean addEjected(String ejected) {
        System.out.println("Enter true if the player has been ejected, false otherwise");
        String isEjected = input.nextLine();
        return Boolean.parseBoolean(isEjected);
    }


    //MODIFIES: this
    //EFFECTS: Retrieves the fantasy scores for both lineups and prints them
    private void getLineupFPoints() {
        System.out.println("Your lineup got " + myLineup.getFantasyScoreLineup() + "fantasy points today");
    }

    // EFFECTS: saves the lineup to file
    private void saveLineup() {
        try {
            jsonWriter.open();
            jsonWriter.write(myLineup);
            jsonWriter.close();
            System.out.println("Saved your lineup to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads lineup from file
    private void loadLineup() {
        try {
            myLineup = jsonReader.read();
            System.out.println("Loaded your lineup from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
