package model;

import model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Code referenced from: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkPlayer(String name, String team, String position, int salary, Player player) {
        assertEquals(name, player.getName());
        assertEquals(team, player.getTeam());
        assertEquals(position, player.getPosition());
        assertEquals(salary, player.getSalary());
    }
}
