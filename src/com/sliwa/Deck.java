package com.sliwa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>(CardRanks.values().length * CardSuits.values().length);
        createADeck();
    }

    public List<Card> getDeck() {
        return deck;
    }

    public enum CardSuits {
        Clubs(50, "♣"),
        Diamonds(100, "♦"),
        Hearts(150, "♥"),
        Spades(200, "♠");

        private final int suitValue;
        private final String suitName;

        CardSuits(int suitValue, String suitName) {
            this.suitValue = suitValue;
            this.suitName = suitName;
        }

        public int getSuitValue() {
            return suitValue;
        }

        public String getSuitName() {
            return suitName;
        }
    }

    public enum CardRanks {
        Two(2, "2"),
        Three(3, "3"),
        Four(4, "4"),
        Five(5, "5"),
        Six(6, "6"),
        Seven(7, "7"),
        Eight(8, "8"),
        Nine(9, "9"),
        Ten(10, "10"),
        Jack(11, "J"),
        Queen(12, "Q"),
        King(13, "K"),
        Ace(14, "A");

        private final int cardValue;
        private final String cardName;

        CardRanks(int cardValue, String cardName) {
            this.cardValue = cardValue;
            this.cardName = cardName;
        }

        public int getCardValue() {
            return cardValue;
        }

        public String getCardName() {
            return cardName;
        }
    }


    public void createADeck() {
        deck.clear();
        for (CardSuits cardSuit : CardSuits.values()) {
            for (CardRanks cardRank : CardRanks.values()) {
                deck.add(new Card(cardRank, cardSuit));
            }
        }
        Collections.shuffle(deck);
    }

    public static class Card implements Comparable<Card> {
        private final CardSuits cardSuit;
        private final CardRanks cardRank;

        public Card(CardRanks cardRank, CardSuits cardSuit) {
            this.cardSuit = cardSuit;
            this.cardRank = cardRank;
        }

        public int getRankValue() {
            return cardRank.getCardValue();
        }
        public int getSuitValue() {
            return cardSuit.getSuitValue();
        }

        @Override
        public String toString() {
            return cardRank.getCardName() + cardSuit.getSuitName();
        }

        @Override
        public int compareTo(Card card) {
            return Integer.compare(card.getSuitValue()
                    + card.getRankValue(), this.getSuitValue() + this.getRankValue());
        }
    }


}
