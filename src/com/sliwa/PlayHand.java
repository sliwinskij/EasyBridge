package com.sliwa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayHand {
    private final Map<Integer, ArrayList<Deck.Card>> cardsPlayed;
    private boolean playHandStarted;
    private Deck.CardSuits roundSuit;
    private int roundCount;
    private String roundWinningPlayer;
    private Deck.Card roundWinningCard;
    private boolean showDummyCards;

    public PlayHand() {
        this.cardsPlayed = new HashMap<>();
        setRoundCount();
        this.playHandStarted = false;
        this.roundCount = 1;
        this.roundWinningPlayer = "";
        this.showDummyCards = false;
    }

    private void setRoundCount() {
        int roundsInGame = 13;
        for (int i = 1; i <= roundsInGame; i++) {
            this.cardsPlayed.put(i, new ArrayList<>());
        }
    }

    public boolean isPlayHandStarted() {
        return playHandStarted;
    }

    public Deck.CardSuits getRoundSuit() {
        return roundSuit;
    }

    public void setRoundSuit(Deck.CardSuits roundSuit) {
            this.roundSuit = roundSuit;
    }

    public void setRoundWinningPlayer(String roundWinningPlayer) {
        this.roundWinningPlayer = roundWinningPlayer;
    }

    public String getRoundWinningPlayer() {
        return roundWinningPlayer;
    }

    public void setRoundWinningCard(Deck.Card roundWinningCard) {
        this.roundWinningCard = roundWinningCard;
    }

    public boolean isShowDummyCards() {
        return showDummyCards;
    }

    public void regulatePlayHand(Player player, int playChoice, Contract contract, Team teamNS, Team teamEW) {

        Deck.Card playedCard = player.getPlayersDeck().get(playChoice - 1);
        if (cardsPlayed.get(roundCount).size() == 0) {
            setRoundSuit(playedCard.getCardSuit());
        }
        player.getPlayersDeck().remove(playedCard);
        player.getClubsDeck().remove(playedCard);
        player.getDiamondsDeck().remove(playedCard);
        player.getHeartsDeck().remove(playedCard);
        player.getSpadesDeck().remove(playedCard);
        if (cardsPlayed.get(roundCount).size() < 4) {
            cardsPlayed.get(roundCount).add(playedCard);
            if (roundWinningCard == null) {
                roundWinningPlayer = player.getDirection();
                roundWinningCard = playedCard;
            } else if (!roundWinningCard.getCardSuit().getSuitName().equalsIgnoreCase(contract.getCurrentContract().substring(1))
                    && playedCard.getCardSuit().getSuitName().equalsIgnoreCase(contract.getCurrentContract().substring(1))) {
                roundWinningPlayer = player.getDirection();
                roundWinningCard = playedCard;
            } else if (roundWinningCard.getCardSuit().getSuitName().equalsIgnoreCase(contract.getCurrentContract().substring(1))
                    && playedCard.getCardSuit().getSuitName().equalsIgnoreCase(contract.getCurrentContract().substring(1))
                    && playedCard.compareTo(roundWinningCard) < 0) {
                roundWinningPlayer = player.getDirection();
                roundWinningCard = playedCard;
            } else if (!roundWinningCard.getCardSuit().getSuitName().equalsIgnoreCase(contract.getCurrentContract().substring(1))
                    && !playedCard.getCardSuit().getSuitName().equalsIgnoreCase(contract.getCurrentContract().substring(1))
                    && playedCard.compareTo(roundWinningCard) < 0 && playedCard.getCardSuit().equals(roundSuit)) {
                roundWinningPlayer = player.getDirection();
                roundWinningCard = playedCard;
            }
        }
        if (!playHandStarted) {
            playHandStarted = true;
        }
        if (cardsPlayed.get(roundCount).size() >= 4) {
            playHandStarted = false;
        }
        if (roundCount == 1 && playHandStarted) {
            showDummyCards = true;
        }
        if (cardsPlayed.get(roundCount).size() == 4) {
                if (teamNS.isPlayerInThisTeam(roundWinningPlayer)) {
                    teamNS.setCardsCollected();
                }
                if (teamEW.isPlayerInThisTeam(roundWinningPlayer)) {
                    teamEW.setCardsCollected();
                }
            roundCount++;
            System.out.println("Round winner: " + roundWinningPlayer);
        }
    }
}
