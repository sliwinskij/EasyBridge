package com.sliwa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>(cardNames.values().length* cardSuits.values().length);
        createADeck();
    }

    public List<Card> getDeck() {
        return deck;
    }

    public enum cardSuits {
        Clubs(50),
        Diamonds(100),
        Hearts(150),
        Spades(200);

        private final int suitValue;

        cardSuits(int suitValue){
            this.suitValue=suitValue;
        }

        public int getSuitValue(){
            return suitValue;
        }
    }

    public enum cardNames {
        Two(2),
        Three(3),
        Four(4),
        Five(5),
        Six(6),
        Seven(7),
        Eight(8),
        Nine(9),
        Ten(10),
        Jack(11),
        Queen(12),
        King(13),
        Ace(14);

        private final int cardValue;

        cardNames(int cardValue){
            this.cardValue=cardValue;
        }

        public int getCardValue(){
            return cardValue;
        }
    }


    public void createADeck(){
        deck.clear();
        for(cardSuits cardSuit : cardSuits.values()){
            for (cardNames cardName : cardNames.values()){
                deck.add(new Card(cardSuit, cardName));
            }
        }
        Collections.shuffle(deck);
    }

    public static class Card implements Comparable<Card>{
        private final cardSuits cardSuit;
        private final cardNames cardName;

        public Card(cardSuits cardSuit, cardNames cardName) {
            this.cardSuit = cardSuit;
            this.cardName = cardName;
        }

        public cardSuits getCardSuit() {
            return cardSuit;
        }

        public cardNames getCardName() {
            return cardName;
        }

        public int getCardValue(){
            return cardName.getCardValue();
        }
        public int getSuitValue(){
            return cardSuit.getSuitValue();
        }

        @Override
        public String toString() {
            return cardName + " of " + cardSuit;
        }

        @Override
        public int compareTo(Card card) {
            return Integer.compare(card.getSuitValue() + card.getCardValue(), this.getSuitValue() + this.getCardValue());
        }
    }


}
