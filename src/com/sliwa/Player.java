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
    private final List<Deck.Card> clubsDeck;
    private final List<Deck.Card> diamondsDeck;
    private final List<Deck.Card> heartsDeck;
    private final List<Deck.Card> spadesDeck;


    public Player(String name, Deck deck, String direction) {
        this.name = name;
        this.playersDeck = dealCardsToPlayer(deck);
        Collections.sort(playersDeck);
        this.direction = direction;
        this.pass = false;
        this.clubsDeck = new ArrayList<>();
        this.diamondsDeck = new ArrayList<>();
        this.heartsDeck = new ArrayList<>();
        this.spadesDeck = new ArrayList<>();
        divideDeckIntoSuits();
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

    public void divideDeckIntoSuits() {
        for (Deck.Card card : playersDeck) {
            if (card.getCardSuit().getSuitName().equalsIgnoreCase("♣")) {
                clubsDeck.add(card);
            }
            if (card.getCardSuit().getSuitName().equalsIgnoreCase("♦")) {
                diamondsDeck.add(card);
            }
            if (card.getCardSuit().getSuitName().equalsIgnoreCase("♥")) {
                heartsDeck.add(card);
            }
            if (card.getCardSuit().getSuitName().equalsIgnoreCase("♠")) {
                spadesDeck.add(card);
            }
        }
    }

    public List<Deck.Card> getClubsDeck() {
        return clubsDeck;
    }

    public List<Deck.Card> getDiamondsDeck() {
        return diamondsDeck;
    }

    public List<Deck.Card> getHeartsDeck() {
        return heartsDeck;
    }

    public List<Deck.Card> getSpadesDeck() {
        return spadesDeck;
    }

    public int showPlayersDeckMenu(PlayHand playHand) {
        int offset = 0;
        int orderNumber = 1;
        boolean alreadyExecuted = false;
        if (playHand.getRoundSuit() != null && playHand.getRoundSuit().getSuitName().equalsIgnoreCase("♣") && clubsDeck.size() > 0) {
            for (int i = 0; i < playersDeck.size(); i++) {
                if (playersDeck.get(i).getCardSuit().equals(playHand.getRoundSuit())) {
                    System.out.print(orderNumber + ". " + playersDeck.get(i) + "\n");
                    orderNumber++;
                    if (!alreadyExecuted){
                        offset = i;
                        alreadyExecuted = true;
                    }
                }
            }
        }
        else if (playHand.getRoundSuit() != null && playHand.getRoundSuit().getSuitName().equalsIgnoreCase("♦") && diamondsDeck.size() > 0) {
            for (int i = 0; i < playersDeck.size(); i++) {
                if (playersDeck.get(i).getCardSuit().equals(playHand.getRoundSuit())) {
                    System.out.print(orderNumber + ". " + playersDeck.get(i) + "\n");
                    orderNumber++;
                    if (!alreadyExecuted){
                        offset = i;
                        alreadyExecuted = true;
                    }
                }
            }
        }
        else if (playHand.getRoundSuit() != null && playHand.getRoundSuit().getSuitName().equalsIgnoreCase("♥") && heartsDeck.size() > 0) {
            for (int i = 0; i < playersDeck.size(); i++) {
                if (playersDeck.get(i).getCardSuit().equals(playHand.getRoundSuit())) {
                    System.out.print(orderNumber + ". " + playersDeck.get(i) + "\n");
                    orderNumber++;
                    if (!alreadyExecuted){
                        offset = i;
                        alreadyExecuted = true;
                    }
                }
            }
        }
        else if (playHand.getRoundSuit() != null && playHand.getRoundSuit().getSuitName().equalsIgnoreCase("♠") && spadesDeck.size() > 0) {
            for (int i = 0; i < playersDeck.size(); i++) {
                if (playersDeck.get(i).getCardSuit().equals(playHand.getRoundSuit())) {
                    System.out.print(orderNumber + ". " + playersDeck.get(i) + "\n");
                    orderNumber++;
                    if (!alreadyExecuted){
                        offset = i;
                        alreadyExecuted = true;
                    }
                }
            }
        } else {
            for (int i = 0; i < playersDeck.size(); i++) {
                System.out.print(i + 1 + ". " + playersDeck.get(i) + "\n");
            }
        }
        return offset;
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

    private List<Deck.Card> dealCardsToPlayer(Deck deck) {
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
