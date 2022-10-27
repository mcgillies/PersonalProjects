package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Code referenced from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.
    Account myAccount;

    @BeforeEach
    void setup() {
        myAccount = new Account(0, "account");

    }

    @Test
    void testWriterInvalidFile() {
        try {
            Lineup lineup = new Lineup(myAccount);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testWriterGeneralLineup() {
        try {
            Lineup lineup = new Lineup(myAccount);
            lineup.addPlayer(new Player("Dave", "SAS", "PG", 50));
            lineup.addPlayer(new Player("John", "SAS", "SG", 30));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLineup.json");
            writer.open();
            writer.write(lineup);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLineup.json");
            lineup = reader.read();
            assertEquals("account", lineup.getAccount().getUsername());
            List<Player> players = lineup.getPlayers();
            assertEquals(2, players.size());
            checkPlayer("Dave", "SAS", "PG", 50, players.get(0));
            checkPlayer("John", "SAS", "SG", 30, players.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
