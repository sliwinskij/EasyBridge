package com.sliwa;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean gameOn = true;
        int choice = 0;
        boolean valid;

        System.out.println("Welcome to EasyBridge!");
        while (gameOn) {
            printInstructions();

            //Choice validation
            do {
                System.out.println("Enter your choice: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    valid = true;
                } else {
                    scanner.nextLine();
                    valid = false;
                    System.out.println("Enter a valid number!");
                    printInstructions();
                }
            } while (!valid);

            switch (choice) {
                case 1:
                    gameOn = false;
                    System.out.println("Bye! Bye!");
                    break;
                case 2:
                    //Initialize deck, players and teams
                    Deck deck = new Deck();

                    System.out.println("Enter player North name:");
                    String name = scanner.nextLine();
                    Player playerN = new Player(name, deck, "N");

                    System.out.println("Enter player East name:");
                    name = scanner.nextLine();
                    Player playerE = new Player(name, deck, "E");

                    System.out.println("Enter player South name:");
                    name = scanner.nextLine();
                    Player playerS = new Player(name, deck, "S");

                    System.out.println("Enter player West name:");
                    name = scanner.nextLine();
                    Player playerW = new Player(name, deck, "W");
                    Team teamNS = new Team(playerN, playerS);
                    Team teamEW = new Team(playerE, playerW);

                    //Bidding Phase
                    Contract contract = new Contract();
                    biddingPhase(playerN, playerE, playerS, playerW, random, scanner, teamNS, teamEW, contract);

                    //Play Phase
                    PlayHand playHand = new PlayHand();
                    System.out.println("Game starts now! Whist player: " + contract.getWhistPlayer() + " starts!");
                    do {
                        playHandPhase(playerN, contract, scanner, playHand, teamNS, teamEW);

                        playHandPhase(playerE, contract, scanner, playHand, teamNS, teamEW);

                        playHandPhase(playerS, contract, scanner, playHand, teamNS, teamEW);

                        playHandPhase(playerW, contract, scanner, playHand, teamNS, teamEW);

                    } while (playerN.getPlayersDeck().size() != 0 && playerE.getPlayersDeck().size() != 0
                            && playerS.getPlayersDeck().size() != 0 && playerW.getPlayersDeck().size() != 0);


                    if (contract.getContractWinners().equalsIgnoreCase(teamNS.getName())) {
                        if (teamNS.getCardsCollected() >= contract.getCardsToWin()) {
                            System.out.println("Winners: " + teamNS.getName() + " points: +" + contract.getActualContractValue(teamNS, teamEW) + " rounds won: " + teamNS.getCardsCollected());
                            System.out.println("Losers: " + teamEW.getName() + " rounds won: " + teamEW.getCardsCollected());
                        } else {
                            System.out.println("Losers: " + teamNS.getName() + " points: -" + contract.getActualContractValue(teamNS, teamEW) + " rounds won: " + teamNS.getCardsCollected());
                            System.out.println("Winners: " + teamEW.getName() + " rounds won: " + teamEW.getCardsCollected());
                        }
                    }

                    if (contract.getContractWinners().equalsIgnoreCase(teamEW.getName())) {
                        if (teamEW.getCardsCollected() >= contract.getCardsToWin()) {
                            System.out.println("Winners: " + teamEW.getName() + " points: +" + contract.getActualContractValue(teamNS, teamEW) + " rounds won: " + teamEW.getCardsCollected());
                            System.out.println("Losers: " + teamNS.getName() + " rounds won: " + teamNS.getCardsCollected());
                        } else {
                            System.out.println("Losers: " + teamEW.getName() + " points: -" + contract.getActualContractValue(teamNS, teamEW) + " rounds won: " + teamEW.getCardsCollected());
                            System.out.println("Winners: " + teamNS.getName() + " rounds won: " + teamNS.getCardsCollected());
                        }
                    }

                    break;
                default:
                    System.out.println("Enter a valid number!");
            }
        }
    }

    public static void printInstructions() {
        System.out.println("1. Quit");
        System.out.println("2. Start Game");
    }

    public static void biddingPhase(Player playerN, Player playerE, Player playerS, Player playerW, Random random, Scanner scanner, Team teamNS, Team teamEW, Contract contract) {
        int chooseStartingPlayer = random.nextInt(4);

        System.out.println("Bidding starts now!");
        while (true) {
            playBid(playerN, chooseStartingPlayer, contract, scanner, 0, teamNS, teamEW);
            if (playerN.isPass() && playerE.isPass() && playerS.isPass() && playerW.isPass()) {
                break;
            }
            if (playerS.isPass() && playerW.isPass() && playerN.isPass() && contract.getBiddingFlow().size() != 0
                    || (contract.getCurrentContract().equals("7NT") && (teamNS.isDoubleVersus() || teamEW.isDoubleVersus()))) {
                break;
            }

            playBid(playerE, chooseStartingPlayer, contract, scanner, 1, teamNS, teamEW);
            if (playerN.isPass() && playerE.isPass() && playerS.isPass() && playerW.isPass()) {
                break;
            }
            if (playerW.isPass() && playerN.isPass() && playerE.isPass() && contract.getBiddingFlow().size() != 0
                    || (contract.getCurrentContract().equals("7NT") && (teamNS.isDoubleVersus() || teamEW.isDoubleVersus()))) {
                break;
            }

            playBid(playerS, chooseStartingPlayer, contract, scanner, 2, teamNS, teamEW);
            if (playerN.isPass() && playerE.isPass() && playerS.isPass() && playerW.isPass()) {
                break;
            }
            if (playerN.isPass() && playerE.isPass() && playerS.isPass() && contract.getBiddingFlow().size() != 0
                    || (contract.getCurrentContract().equals("7NT") && (teamNS.isDoubleVersus() || teamEW.isDoubleVersus()))) {
                break;
            }

            playBid(playerW, chooseStartingPlayer, contract, scanner, 3, teamNS, teamEW);
            if (playerN.isPass() && playerE.isPass() && playerS.isPass() && playerW.isPass()) {
                break;
            }
            if (playerE.isPass() && playerS.isPass() && playerW.isPass() && contract.getBiddingFlow().size() != 0
                    || (contract.getCurrentContract().equals("7NT") && (teamNS.isDoubleVersus() || teamEW.isDoubleVersus()))) {
                break;
            }
        }

        contract.setWhistPlayer();
        System.out.println("Contract winners: " + contract.getContractWinners()
                + ", Whist player: " + contract.getWhistPlayer()
                + ", Contract: " + contract.getCurrentContract()
                + ", lefts to take: " + contract.getCardsToWin()
                + ", contract value: " + contract.getActualContractValue(teamNS, teamEW));

        System.out.println("Contract flow: " + contract.getContractFlow());
    }

    public static void playBid(Player player, int chooseStartingPlayer, Contract contract, Scanner scanner, int number, Team teamNS, Team teamEW) {
        if (chooseStartingPlayer == number || contract.isContractStarted()) {
            int bidChoice = 0;
            boolean valid;

            System.out.println("Your card's: " + player.getPlayersDeck());
            System.out.println("Your point's: " + player.getPlayerPoints());
            if (contract.isContractStarted()) {
                System.out.println("Actual contract: " + contract.getCurrentContract());
                System.out.println("Contract flow: " + contract.getContractFlow());
            }
            System.out.println(player.getName()  + " " + player.getDirection() + " choose your action: ");

            contract.changeBiddingBoxChoices(player, teamNS, teamEW);
            contract.showBiddingBoxMenu();

            //Choice validation
            do {
                if (scanner.hasNextInt()) {
                    bidChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (bidChoice >= 1 && bidChoice <= contract.getBiddingBox().size()) {
                        valid = true;
                    } else {
                        valid = false;
                        System.out.println("Enter a valid number!");
                    }
                } else {
                    scanner.nextLine();
                    valid = false;
                    System.out.println("Enter a valid number!");
                }
            } while (!valid);

            contract.setCurrentContract(bidChoice);
            contract.setContractFlow(bidChoice, player);
            contract.setContractPlayer(bidChoice , player);
            contract.regulateBiddingBoxAndContract(bidChoice, player, teamNS, teamEW);

            if (!contract.isContractStarted()) {
                contract.setContractStarted(true);
            }
        }
    }

    public static void playHandPhase(Player player, Contract contract, Scanner scanner, PlayHand playHand, Team teamNS, Team teamEW) {
        if ((player.getDirection().equalsIgnoreCase(contract.getWhistPlayer()) && playHand.getRoundWinningPlayer().equalsIgnoreCase("") && !playHand.isPlayHandStarted())
                || (playHand.getRoundWinningPlayer().equalsIgnoreCase(player.getDirection()) && !playHand.isPlayHandStarted())
                || playHand.isPlayHandStarted()) {
            boolean valid;
            int playChoice = 0;
            int offset;
            Player dummyPlayer = null;
            System.out.println("Contract: " + contract.getCurrentContract());
            if (contract.getContractWinners().equalsIgnoreCase("NS")) {
                dummyPlayer = teamNS.getDummyPlayer(contract);
            }

            if (contract.getContractWinners().equalsIgnoreCase("EW")) {
                dummyPlayer = teamEW.getDummyPlayer(contract);
            }

            if (playHand.isShowDummyCards() && dummyPlayer != null && dummyPlayer.getPlayersDeck().size() > 0) {
                System.out.println("Dummy's cards: " + dummyPlayer.getPlayersDeck());
            }

            if (dummyPlayer == null || !player.getDirection().equalsIgnoreCase(dummyPlayer.getDirection())) {
                System.out.println("Your hand: " + player.getPlayersDeck());
            }

            System.out.println("Player " + player.getDirection() + " choose your action:");
            if (!playHand.isPlayHandStarted()) {
                playHand.setRoundSuit(null);
                playHand.setRoundWinningCard(null);
                playHand.setRoundWinningPlayer("N/A");
            }

            offset = player.showPlayersDeckMenu(playHand);

            //Choice validation
            do {
                if (scanner.hasNextInt()) {
                    playChoice = scanner.nextInt();
                    scanner.nextLine();
                        if (playHand.getRoundSuit() != null && playHand.getRoundSuit().equals(Deck.CardSuits.Clubs)
                                && playChoice <= player.getClubsDeck().size() && player.getClubsDeck().size() > 0) {
                            valid = true;
                        } else if (playHand.getRoundSuit() != null && playHand.getRoundSuit().equals(Deck.CardSuits.Diamonds)
                                && playChoice <= player.getDiamondsDeck().size() && player.getDiamondsDeck().size() > 0) {
                            valid = true;
                        } else if (playHand.getRoundSuit() != null && playHand.getRoundSuit().equals(Deck.CardSuits.Hearts)
                                && playChoice <= player.getHeartsDeck().size() && player.getHeartsDeck().size() > 0) {
                            valid = true;
                        } else if (playHand.getRoundSuit() != null && playHand.getRoundSuit().equals(Deck.CardSuits.Spades)
                                && playChoice <= player.getSpadesDeck().size() && player.getSpadesDeck().size() > 0) {
                            valid = true;
                        } else if (playChoice >= 1 && playChoice <= player.getPlayersDeck().size()
                                && (playHand.getRoundSuit() == null
                                || (playHand.getRoundSuit().equals(Deck.CardSuits.Clubs) && player.getClubsDeck().size() == 0)
                                || (playHand.getRoundSuit().equals(Deck.CardSuits.Diamonds) && player.getDiamondsDeck().size() == 0)
                                || (playHand.getRoundSuit().equals(Deck.CardSuits.Hearts) && player.getHeartsDeck().size() == 0)
                                || playHand.getRoundSuit().equals(Deck.CardSuits.Spades) && player.getSpadesDeck().size() == 0)) {
                            valid = true;
                        } else {
                        valid = false;
                        System.out.println("Enter a valid number!");
                    }
                } else {
                    scanner.nextLine();
                    valid = false;
                    System.out.println("Enter a valid number!");
                }
            } while (!valid);

            playHand.regulatePlayHand(player, playChoice + offset, contract, teamNS, teamEW);
        }
    }
}
