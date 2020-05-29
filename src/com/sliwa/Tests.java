package com.sliwa;

import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {

    Deck deck = new Deck();
    private Player player1 = new Player("Roland",deck,"S");
    private Player player2 = new Player("Kuba",deck,"N");
    Team team = new Team(player1,player2);


    @Test
    public void getAnotherPlayerFromTeam(){
        team.setVersus(player1);
        assertTrue(team.isVersus());
    }


}