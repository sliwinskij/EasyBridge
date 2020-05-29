package com.sliwa;

public class Team {
    private final Player player1;
    private final Player player2;
    private final String name;
    private boolean versus = false;
    private boolean doubleVersus = false;

    public Team(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.name = player1.getDirection()+player2.getDirection();
    }

    public boolean isPlayerInThisTeam(Player player){
        if (this.player1.getDirection().equals(player.getDirection())){
            return true;
        }
        return this.player2.getDirection().equals(player.getDirection());
    }

    public void setVersus(Player player){
        if (!doubleVersus) {
            if (isPlayerInThisTeam(player)) {
                versus = true;
            }
        }
    }

    public void setDoubleVersus(Player player){
        if (!versus) {
            if (isPlayerInThisTeam(player)) {
                doubleVersus = true;
            }
        }
    }

    public boolean isVersus() {
        return versus;
    }

    public boolean isDoubleVersus() {
        return doubleVersus;
    }

    public String getName() {
        return name;
    }
}
