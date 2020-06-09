package com.sliwa;

public class Team {
    private final Player player1;
    private final Player player2;
    private final String name;
    private boolean versus;
    private boolean doubleVersus;
    private int cardsCollected;

    public Team(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.name = player1.getDirection() + player2.getDirection();
        this.versus = false;
        this.doubleVersus = false;
        this.cardsCollected = 0;
    }

    public boolean isPlayerInThisTeam(Player player) {
        if (this.player1.getDirection().equals(player.getDirection())) {
            return true;
        }
        return this.player2.getDirection().equals(player.getDirection());
    }

    public Player getDummyPlayer(Contract contract) {
        if (contract.getContractWinners().equalsIgnoreCase(this.name)) {
            if (contract.getContractPlayer().equalsIgnoreCase(player1.getDirection())) {
                return player2;
            }
            if (contract.getContractPlayer().equalsIgnoreCase(player2.getDirection())) {
                return player1;
            }
        }
        return null;
    }

    public boolean isPlayerInThisTeam(String playerDirection) {
        if (this.player1.getDirection().equals(playerDirection)) {
            return true;
        }
        return this.player2.getDirection().equals(playerDirection);
    }

    public boolean isContractOnThisTeam(Contract contract) {
        if (contract.getContractPlayer().equals(player1.getDirection())) {
            return true;
        }
        return contract.getContractPlayer().equals(player2.getDirection());
    }

    public void setVersus(boolean versus) {
        this.versus = versus;
    }

    public boolean isVersus() {
        return versus;
    }

    public void setDoubleVersus(boolean doubleVersus) {
        this.doubleVersus = doubleVersus;
    }

    public boolean isDoubleVersus() {
        return doubleVersus;
    }

    public int getCardsCollected() {
        return cardsCollected;
    }

    public void setCardsCollected() {
        this.cardsCollected++;
    }

    public String getName() {
        return name;
    }
}
