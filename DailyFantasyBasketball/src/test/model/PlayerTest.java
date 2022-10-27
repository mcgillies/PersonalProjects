package model;

import static model.Player.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class PlayerTest {
    private Player dejounte;

    @BeforeEach
    void runBefore() {dejounte = new Player("Dejounte Murray",
            "SAS", "PG", 100);}

    @Test
    void testAddStatsNothing() {
        dejounte.addStats(0,0,0,0,0,0, false);
        assertEquals(0, dejounte.getPoints());
        assertEquals(0, dejounte.getRebounds());
        assertEquals(0, dejounte.getBlocks());
        assertFalse(dejounte.getEjected());
    }

    @Test
    void testAddStatsSomething() {
        dejounte.addStats(20, 2, 8,10,1,
                2,true);
        assertEquals(2, dejounte.getThrees());
        assertEquals(10, dejounte.getAssists());
        assertEquals(2, dejounte.getSteals());
        assertTrue(dejounte.getEjected());
    }

    @Test
    void testCalculateFantasyPointsNotEjected() {
        dejounte.addStats(30, 4, 10, 12,
                3, 0, false);
        assertEquals(87, dejounte.calculateFantasyPoints());
        assertEquals(87, dejounte.getFpoints());
    }

    @Test
    void testCalculateFantasyPointsEjected() {
        dejounte.addStats(10, 0, 4, 6,
                1, 1, true);
        assertEquals(56, dejounte.calculateFantasyPoints());
    }

    @Test
    void testCreatedPlayer() {
        assertEquals("Dejounte Murray", dejounte.getName());
        assertEquals("SAS", dejounte.getTeam());
        assertEquals("PG", dejounte.getPosition());
        assertEquals(100, dejounte.getSalary());
    }
}