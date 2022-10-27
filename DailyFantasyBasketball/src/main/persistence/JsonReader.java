package persistence;

import model.Account;
import model.Lineup;
import model.Player;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;


//Code Referenced from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads lineup from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Lineup read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }


    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses lineup from JSON object and returns it
    private Lineup parseWorkRoom(JSONObject jsonObject) {
        int accountBal = jsonObject.getInt("Account balance");
        String accountName = jsonObject.getString("Account name");
        Lineup lineup = new Lineup(new Account(accountBal, accountName));
        addPlayers(lineup, jsonObject);
        return lineup;
    }

    // MODIFIES: wr
    // EFFECTS: parses players from JSON object and adds them to lineup
    private void addPlayers(Lineup lineup, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Players");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addPlayer(lineup, nextThingy);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addPlayer(Lineup lineup, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String team = jsonObject.getString("team");
        String position = jsonObject.getString("position");
        int salary = jsonObject.getInt("salary");
        Player player = new Player(name, team, position, salary);
        lineup.addPlayer(player);
    }
}
