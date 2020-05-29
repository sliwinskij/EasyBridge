package com.sliwa;

import java.util.*;

public class Contract {
    private final List<String> biddingBox;
    private String currentContract;
    private boolean contractStarted = false;
    private String contractPlayer;
    private final Map<String,ArrayList<String>> playersBidContract = new HashMap<>();

    public Contract() {
        this.biddingBox = createBiddingBox();
    }

    public void showBiddingBoxMenu(){
        for (int i = 0; i < biddingBox.size(); i++){
            System.out.print(i+1 + ". " + biddingBox.get(i) + "\n");
        }
    }
    public void removeAllBidChoicesBeforeChosenOne(int number){
        if (number > 1) {
            biddingBox.subList(3, number).clear();
        }
    }
    public String getCurrentContract(){
        if (currentContract==null){
            return "NO CONTRACT ACQUIRED";
        }
        return currentContract;
    }

    public void setCurrentContract(int number,Player player) {
        currentContract = biddingBox.get(number-1);
        if (!playersBidContract.containsKey(player.getDirection())){
            playersBidContract.put(player.getDirection(),new ArrayList<>());
        }
        playersBidContract.get(player.getDirection()).add(currentContract);
    }

    public Map<String, ArrayList<String>> getPlayersBidContract() {
        return playersBidContract;
    }

    public boolean isContractStarted() {
        return contractStarted;
    }

    public List<String> getBiddingBox() {
        return biddingBox;
    }

    public void setContractStarted(boolean contractStarted) {
        this.contractStarted = contractStarted;
    }

    private List<String> createBiddingBox(){
        List<String> biddingBox = new ArrayList<>();
        biddingBox.add("PASS");
        biddingBox.add("X");
        biddingBox.add("XX");
        for (bidding bid : bidding.values()){
            biddingBox.add(bid.getContractName());
        }
        return biddingBox;
    }

    public enum bidding {
        CLUBS1("1♣",70,7),
        DIAMONDS1("1♦",70,7),
        HEARTS1("1♥",80,7),
        SPADES1("1♠",80,7),
        NT1("1NT",90,7),

        CLUBS2("2♣",90,8),
        DIAMONDS2("2♦",90,8),
        HEARTS2("2♥",110,8),
        SPADES2("2♠",110,8),
        NT2("2NT",120,8),

        CLUBS3("3♣",110,9),
        DIAMONDS3("3♦",110,9),
        HEARTS3("3♥",140,9),
        SPADES3("3♠",140,9),
        NT3("3NT",400,9),

        CLUBS4("4♣",130,10),
        DIAMONDS4("4♦",130,10),
        HEARTS4("4♥",420,10),
        SPADES4("4♠",420,10),
        NT4("4NT",430,10),

        CLUBS5("5♣",400,11),
        DIAMONDS5("5♦",400,11),
        HEARTS5("5♥",450,11),
        SPADES5("5♠",450,11),
        NT5("5NT",460,11),

        CLUBS6("6♣",920,12),
        DIAMONDS6("6♦",920,12),
        HEARTS6("6♥",980,12),
        SPADES6("6♠",980,12),
        NT6("6NT",990,12),

        CLUBS7("7♣",1440,13),
        DIAMONDS7("7♦",1440,13),
        HEARTS7("7♥",1510,13),
        SPADES7("7♠",1510,13),
        NT7("7NT",1520,13);

        private final int contractValue;
        private final String name;
        private final int cardsToWin;
        private static final Map<String,bidding> ENUM_MAP;

        bidding(String name, int contractValue, int cardsToWin){
            this.contractValue=contractValue;
            this.name = name;
            this.cardsToWin = cardsToWin;
        }

        public int getContractValue(){
            return contractValue;
        }
        public String getContractName(){
            return name;
        }

        public int getCardsToWin() {
            return cardsToWin;
        }

        static {
            Map<String,bidding> map = new HashMap<>();
            for (bidding bid : bidding.values()) {
                map.put(bid.getContractName(),bid);
            }
            ENUM_MAP = Collections.unmodifiableMap(map);
        }

        public static bidding getSingleEnumByContractName (String name) {
            return ENUM_MAP.get(name);
        }

    }
}
