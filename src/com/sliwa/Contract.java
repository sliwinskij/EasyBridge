package com.sliwa;

import java.util.*;

public class Contract {
    private final List<String> biddingBox;
    private String currentContract;
    private boolean contractStarted;
    private String contractPlayer;
    //All bids
    private final List<String> contractFlow = new ArrayList<>();
    //All bids except PASS, X, XX
    private final List<String> biddingFlow = new ArrayList<>();

    public Contract() {
        this.biddingBox = createBiddingBox();
        this.currentContract = "N/A";
        this.contractStarted = false;
        this.contractPlayer = "";
    }

    public boolean isContractStarted() {
        return contractStarted;
    }

    public void setContractStarted(boolean contractStarted) {
        this.contractStarted = contractStarted;
    }

    public void showBiddingBoxMenu() {
        for (int i = 0; i < biddingBox.size(); i++) {
            System.out.print(i + 1 + ". " + biddingBox.get(i) + "\n");
        }
    }

    public String getContractPlayer() {
        return contractPlayer;
    }


    public void setContractPlayer(int number, Player player) {
        if (!biddingBox.get(number - 1).equalsIgnoreCase("PASS")
                && !biddingBox.get(number - 1).equalsIgnoreCase("X")
                && !biddingBox.get(number - 1).equalsIgnoreCase("XX")) {
            contractPlayer = player.getDirection();
        }
    }

    public List<String> getContractFlow() {
        return contractFlow;
    }

    public List<String> getBiddingFlow() {
        return biddingFlow;
    }

    public void setContractFlow(int number, Player player) {
        int i = contractFlow.isEmpty() ? 1 : contractFlow.size() + 1;
        contractFlow.add(i + "." + player.getDirection() + ": " + biddingBox.get(number - 1));

        if (!biddingBox.get(number - 1).equalsIgnoreCase("PASS")
                && !biddingBox.get(number - 1).equalsIgnoreCase("X")
                && !biddingBox.get(number - 1).equalsIgnoreCase("XX")) {
            biddingFlow.add(i + "." + player.getDirection() + ": " + biddingBox.get(number - 1));
        }
    }

    public void setCurrentContract(int number) {
        if (!biddingBox.get(number - 1).equalsIgnoreCase("PASS")
                && !biddingBox.get(number - 1).equalsIgnoreCase("X")
                && !biddingBox.get(number - 1).equalsIgnoreCase("XX")) {
            currentContract = biddingBox.get(number - 1);
        }
    }

    public String getCurrentContract() {
        return currentContract;
    }

    public void regulateBiddingBoxAndContract(int number, Player player, Team teamNS, Team teamEW) {
        String selection = biddingBox.get(number - 1);

        //Set pass
        if (selection.equalsIgnoreCase("PASS")) {
            player.setPass(true);
        }

        //Set versus, remove from box
        if (selection.equalsIgnoreCase("X")) {
            if (teamNS.isPlayerInThisTeam(player)) {
                teamNS.setVersus(true);
            }
            if (teamEW.isPlayerInThisTeam(player)) {
                teamEW.setVersus(true);
            }
            biddingBox.remove("X");
            player.setPass(false);
        }

        //Set double versus, remove from box
        if (selection.equalsIgnoreCase("XX")) {
            if (teamNS.isPlayerInThisTeam(player)) {
                teamNS.setDoubleVersus(true);
            }
            if (teamEW.isPlayerInThisTeam(player)) {
                teamEW.setDoubleVersus(true);
            }
            biddingBox.remove("XX");
            player.setPass(false);
        }

        //Remove all choices before chosen one excluding pass, versus and double versus
        if (!selection.equalsIgnoreCase("PASS")
                && !selection.equalsIgnoreCase("X")
                && !selection.equalsIgnoreCase("XX")) {
            biddingBox.subList(0, number).clear();
            teamNS.setVersus(false);
            teamEW.setVersus(false);
            teamNS.setDoubleVersus(false);
            teamEW.setDoubleVersus(false);
            player.setPass(false);
        }

    }

    public void changeBiddingBoxChoices(Player player, Team teamNS, Team teamEW) {
        boolean oppositeTeamIsVersus = false;

        //check if opposite team is versus
        if (teamNS.isPlayerInThisTeam(player)) {
            oppositeTeamIsVersus = teamEW.isVersus();
        } else if (teamEW.isPlayerInThisTeam(player)) {
            oppositeTeamIsVersus = teamNS.isVersus();
        }

        //add double versus if opposite team is versus and there is no double versus available
        if (oppositeTeamIsVersus) {
            if ((!teamNS.isDoubleVersus() && !teamEW.isDoubleVersus())
                    && !biddingBox.get(biddingBox.size() - 1).equalsIgnoreCase("XX")) {
                biddingBox.add("XX");
            }
        }

        //remove double versus if opposite team is not versus
        if (!oppositeTeamIsVersus) {
            biddingBox.remove("XX");
        }

        //if other team owns contract add versus
        if (teamNS.isContractOnThisTeam(this)) {
            if (!teamNS.isPlayerInThisTeam(player) && !biddingBox.get(biddingBox.size() - 1).equalsIgnoreCase("X")
                    && !teamNS.isVersus() && !teamEW.isVersus()) {
                biddingBox.add("X");
            }
            if (teamNS.isPlayerInThisTeam(player) && biddingBox.get(biddingBox.size() - 1).equalsIgnoreCase("X")) {
                biddingBox.remove("X");
            }
        }

        //if other team owns contract add versus
        if (teamEW.isContractOnThisTeam(this)) {
            if (!teamEW.isPlayerInThisTeam(player) && !biddingBox.get(biddingBox.size() - 1).equalsIgnoreCase("X")
                    && !teamEW.isVersus() && !teamNS.isVersus()) {
                biddingBox.add("X");
            }
            if (teamEW.isPlayerInThisTeam(player) && biddingBox.get(biddingBox.size() - 1).equalsIgnoreCase("X")) {
                biddingBox.remove("X");
            }
        }
    }

    public String getWhistPlayer() {
        //Determine the whist player:
        //one to the left of first player to bid on colour in actual contract.
        for (String flow : biddingFlow) {
            if (contractPlayer.equals("N") || contractPlayer.equals("S")) {
                if (flow.startsWith("N", 2) && flow.substring(6).equals(currentContract.substring(1))) {
                    return "E";
                }
                if (flow.startsWith("S", 2) && flow.substring(6).equals(currentContract.substring(1))) {
                    return "W";
                }
            }
            if (contractPlayer.equals("E") || contractPlayer.equals("W")) {
                if (flow.startsWith("E", 2) && flow.substring(6).equals(currentContract.substring(1))) {
                    return "S";
                }
                if (flow.startsWith("W", 2) && flow.substring(6).equals(currentContract.substring(1))) {
                    return "N";
                }
            }
        }
        return "N/A";
    }

    public String getContractWinners(Team teamNS, Team teamEW) {
        if (contractPlayer.startsWith("N")) {
            return teamNS.getName();
        }
        if (contractPlayer.startsWith("S")) {
            return teamNS.getName();
        }
        if (contractPlayer.startsWith("E")) {
            return teamEW.getName();
        }
        if (contractPlayer.startsWith("W")) {
            return teamEW.getName();
        }
        return "N/A";
    }

    private List<String> createBiddingBox() {
        List<String> biddingBox = new ArrayList<>();
        for (Bidding bid : Bidding.values()) {
            biddingBox.add(bid.getContractName());
        }
        biddingBox.add("PASS");
        return biddingBox;
    }

    public List<String> getBiddingBox() {
        return biddingBox;
    }

    public int getActualContractValue(Team teamNS, Team teamEW) {
        if (teamNS.isDoubleVersus() || teamEW.isDoubleVersus()) {
            return Bidding.getEnum(currentContract).getContractDoubleVersusValue();
        } else if (teamNS.isVersus() || teamEW.isVersus()) {
            return Bidding.getEnum(currentContract).getContractVersusValue();
        } else if (!currentContract.equals("N/A")) {
            return Bidding.getEnum(currentContract).getContractValue();
        }
        return 0;
    }

    public int getCardsToWin() {
        if (currentContract.equals("N/A")){
            return 0;
        }
        return Bidding.getEnum(currentContract).getCardsToWin();
    }

    public enum Bidding {
        CLUBS1("1♣", 70, 7, 140, 230),
        DIAMONDS1("1♦", 70, 7, 140, 230),
        HEARTS1("1♥", 80, 7, 160, 520),
        SPADES1("1♠", 80, 7, 160, 520),
        NT1("1NT", 90, 7, 180, 560),

        CLUBS2("2♣", 90, 8, 180, 560),
        DIAMONDS2("2♦", 90, 8, 180, 560),
        HEARTS2("2♥", 110, 8, 470, 640),
        SPADES2("2♠", 110, 8, 470, 640),
        NT2("2NT", 120, 8, 490, 680),

        CLUBS3("3♣", 110, 9, 470, 640),
        DIAMONDS3("3♦", 110, 9, 470, 640),
        HEARTS3("3♥", 140, 9, 530, 760),
        SPADES3("3♠", 140, 9, 530, 760),
        NT3("3NT", 400, 9, 550, 800),

        CLUBS4("4♣", 130, 10, 510, 720),
        DIAMONDS4("4♦", 130, 10, 510, 720),
        HEARTS4("4♥", 420, 10, 590, 880),
        SPADES4("4♠", 420, 10, 590, 880),
        NT4("4NT", 430, 10, 610, 920),

        CLUBS5("5♣", 400, 11, 550, 800),
        DIAMONDS5("5♦", 400, 11, 550, 800),
        HEARTS5("5♥", 450, 11, 650, 1000),
        SPADES5("5♠", 450, 11, 650, 1000),
        NT5("5NT", 460, 11, 670, 1040),

        CLUBS6("6♣", 920, 12, 1090, 1380),
        DIAMONDS6("6♦", 920, 12, 1090, 1380),
        HEARTS6("6♥", 980, 12, 1210, 1620),
        SPADES6("6♠", 980, 12, 1210, 1620),
        NT6("6NT", 990, 12, 1230, 1660),

        CLUBS7("7♣", 1440, 13, 1630, 1960),
        DIAMONDS7("7♦", 1440, 13, 1630, 1960),
        HEARTS7("7♥", 1510, 13, 1770, 2240),
        SPADES7("7♠", 1510, 13, 1770, 2240),
        NT7("7NT", 1520, 13, 1790, 2280);

        private final int contractValue;
        private final String name;
        private final int cardsToWin;
        private final int contractVersusValue;
        private final int contractDoubleVersusValue;
        private static final Map<String, Bidding> ENUM_MAP;

        Bidding(String name, int contractValue, int cardsToWin, int contractVersusValue, int contractDoubleVersusValue) {
            this.contractValue = contractValue;
            this.name = name;
            this.cardsToWin = cardsToWin;
            this.contractVersusValue = contractVersusValue;
            this.contractDoubleVersusValue = contractDoubleVersusValue;
        }

        public int getContractValue() {
            return contractValue;
        }

        public String getContractName() {
            return name;
        }

        public int getCardsToWin() {
            return cardsToWin;
        }

        public int getContractVersusValue() {
            return contractVersusValue;
        }

        public int getContractDoubleVersusValue() {
            return contractDoubleVersusValue;
        }

        static {
            Map<String, Bidding> map = new HashMap<>();
            for (Bidding bid : Bidding.values()) {
                map.put(bid.getContractName(), bid);
            }
            ENUM_MAP = Collections.unmodifiableMap(map);
        }

        public static Bidding getEnum(String name) {
            return ENUM_MAP.get(name);
        }

    }
}
