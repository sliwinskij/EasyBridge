package com.sliwa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Player {
    private final String name;
    private final List<Deck.Card> playersDeck;
    private final String direction;
    private boolean pass;


    public Player(String name, Deck deck, String direction) {
        this.name = name;
        this.playersDeck = dealCardsToPlayers(deck);
        Collections.sort(playersDeck);
        this.direction = direction;
        this.pass = false;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getDirection() {
        return direction;
    }

    public String getName() {
        return name;
    }

    public List<Deck.Card> getPlayersDeck() {
        return playersDeck;
    }
    public int getPlayerPoints() {
        int sum = 0;
        for (Deck.Card card : playersDeck) {
            if (card.getRankValue() <= 10) {
                sum += 0;
            } else {
                sum += card.getRankValue() - 10;
            }
        }
        return sum;
    }

    private List<Deck.Card> dealCardsToPlayers(Deck deck) {
        Random random = new Random();
        List<Deck.Card> playersDeck = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            int randomDeckCard = random.nextInt(deck.getDeck().size());
            playersDeck.add(deck.getDeck().get(randomDeckCard));
            deck.getDeck().remove(randomDeckCard);
        }
        return playersDeck;
    }




}
