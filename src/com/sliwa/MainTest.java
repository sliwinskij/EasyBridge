package com.sliwa;

import org.junit.Test;

public class MainTest {

    @Test
    public void playerPoints() {
        Deck deck = new Deck();
        Player player1 = new Player("Siemka", deck, "N");
        System.out.println(player1.getPlayersDeck());


        System.out.println();
        System.out.println(player1.playerPoints());
    }
    @Test
    public void contractValue(){
        System.out.println(Contract.bidding.getSingleEnumByContractName("1♣").getContractValue());

    }
}