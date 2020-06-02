package com.sliwa;

import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {

    private final Deck deck = new Deck();
    private final Player player1 = new Player("Roland", deck, "S");
    private final Player player2 = new Player("Kuba", deck, "N");
    private final Team team = new Team(player1, player2);


    @Test
    public void getAnotherPlayerFromTeam() {
        team.setVersus(true);
        assertTrue(team.isVersus());
    }
}
