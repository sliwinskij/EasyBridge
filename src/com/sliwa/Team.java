package com.sliwa;

public class Team {
    private final Player player1;
    private final Player player2;
    private final String name;
    private boolean versus = false;
    private boolean doubleVersus = false;

    public Team(final Player player1, final Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.name = player1.getDirection() + player2.getDirection();
    }

    public boolean isPlayerInThisTeam(final Player player) {
        if (this.player1.getDirection().equals(player.getDirection())) {
            return true;
        }
        return this.player2.getDirection().equals(player.getDirection());
    }

//    public boolean isPlayerInThisTeam(final String playerDirection) {
//        if (this.player1.getDirection().equals(playerDirection)) {
//            return true;
//        }
//        return this.player2.getDirection().equals(playerDirection);
//    }
//
//    public String getOtherPlayerDirection(Player player) {
//        if (isPlayerInThisTeam(player)) {
//            if (player1.getDirection().equals(player.getDirection())) {
//                return player2.getDirection();
//            }
//        }
//        if (isPlayerInThisTeam(player)) {
//            if (player2.getDirection().equals(player.getDirection())) {
//                return player1.getDirection();
//            }
//        }
//        return null;
//    }

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

    public String getName() {
        return name;
    }
}
