package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Code referenced from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    Account myAccount;

    @BeforeEach
    public void setup() {
        myAccount = new Account(150, "fantasyking");
    }


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Lineup lineup = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testReaderFullLineup() {
        JsonReader reader = new JsonReader("./data/FullLineup.json");
        try {
            Lineup lineup = reader.read();
            assertEquals("fantasyking", lineup.getAccount().getUsername());
            List<Player> players = lineup.getPlayers();
            assertEquals(10, players.size());
            checkPlayer("d", "d", "d", 1, players.get(0));
            checkPlayer("d", "d", "d", 1, players.get(1));
            checkPlayer("d", "d", "d", 1, players.get(2));
            checkPlayer("d", "d", "d", 1, players.get(3));
            checkPlayer("d", "d", "d", 1, players.get(4));
            checkPlayer("d", "d", "d", 1, players.get(5));
            checkPlayer("d", "d", "d", 1, players.get(6));
            checkPlayer("d", "d", "d", 1, players.get(7));
            checkPlayer("d", "d", "d", 1, players.get(8));
            checkPlayer("d", "d", "d", 1, players.get(9));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}